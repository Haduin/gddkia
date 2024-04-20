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
import { updatePartialState } from './commons';


export const UserContext = createContext(null);
const App = () => {
  const [userData, setUserData] = useState({
    isAuthenticated: false,
    error: {
      active: false,
      message: ''
    }
  });

  useEffect(() => {
    console.log(userData);
  }, [userData]);

  useEffect(() => {
    if (localStorage.getItem('token') != null) {
      const token = localStorage.getItem('token');
      if(token === 'undefined'){
        localStorage.clear()
      }
      const tokenData = JSON.parse(window.atob(token.split('.')[1]));
      const tokenExp = parseInt(tokenData.exp + '000');

      if (new Date().getTime() < tokenExp) {
        updatePartialState(setUserData, { isAuthenticated : true })
      } else if (new Date().getTime() > tokenExp) {
        updatePartialState(setUserData, { isAuthenticated : false })
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


  return (
    <ThemeCustomization>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <UserContext.Provider value={{ userData, setUserData }}>
          <ScrollTop>
            {userData.isAuthenticated ? <Routes /> : <Login />}
          </ScrollTop>
        </UserContext.Provider>
      </LocalizationProvider>
    </ThemeCustomization>
  );
};

export default App;
