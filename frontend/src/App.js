// project import
import React, { createContext, useEffect, useState } from 'react';
import Routes from 'routes';
import ThemeCustomization from 'themes';
import ScrollTop from 'components/ScrollTop';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import Login from './pages/authentication/Login';
import axios from 'axios';
import { redirect } from 'react-router-dom';
import config from './config';
// ==============================|| APP - THEME, ROUTER, LOCAL  ||============================== //


export const UserContext = createContext(null);
const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userData, setUserData] = useState({
    email: '',
    password: ''
  });

  useEffect(() => {
    if (userData.email !== '') {
      handleLogin();
    }
  }, [userData]);

  useEffect(() => {
    if (localStorage.getItem('token') != null) {
      const token = localStorage.getItem('token');
      var tokenData = JSON.parse(window.atob(token.split('.')[1]));
      var tokenExp = parseInt(tokenData.exp + '000');

      if (new Date().getTime() < tokenExp) {
        setIsAuthenticated(true);
      } else if (new Date().getTime() > tokenExp) {
        setIsAuthenticated(false);
        localStorage.clear();
        window.location.reload(true);
        redirect('/');
      }
      axios.interceptors.request.use((config) => {
        config.headers.Authorization = 'Bearer ' + token;
        config.headers['Access-Control-Allow-Origin'] = '*';
        return config;
      });
    }
  }, []);

  const handleLogin = () => {
    const data = {
      'username': userData.email,
      'password': userData.password
    };
    axios.post(`${config.backend}/login`, data)
      .then(response => {
        localStorage.setItem('token', response.data);
        setIsAuthenticated(true);
        window.location.reload(true);
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (
    <ThemeCustomization>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <UserContext.Provider value={{ userData, setUserData }}>
          <ScrollTop>
            {isAuthenticated ? <Routes /> : <Login />}
          </ScrollTop>
        </UserContext.Provider>
      </LocalizationProvider>
    </ThemeCustomization>
  );
};

export default App;
