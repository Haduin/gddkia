import { useEffect, useState } from 'react';
import axios from 'axios';
import { handleLoginAction } from '../pages/authentication/actions';
import { useNavigate } from 'react-router-dom';

export const useAuthentication = () => {
  const navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [error, setError] = useState({
    active: false,
    message: ''
  });

  useEffect(() => {
    if (localStorage.getItem('token') != null) {
      const token = localStorage.getItem('token');
      if (token === 'undefined') {
        localStorage.clear();
      }
      const tokenData = JSON.parse(window.atob(token.split('.')[1]));
      const tokenExp = parseInt(tokenData.exp + '000');

      if (new Date().getTime() < tokenExp) {
        setIsAuthenticated(true);
      } else if (new Date().getTime() > tokenExp) {
        setIsAuthenticated(false);
        handleLogout();
      }
      axios.interceptors.request.use((config) => {
        config.headers.Authorization = 'Bearer ' + token;
        config.headers['Access-Control-Allow-Origin'] = '*';
        return config;
      });
    }
  }, []);

  const handleLogin = ({ email, password }) => {
    const data = {
      'username': email,
      'password': password
    };
    handleLoginAction(data)
      .then(response => {
        if (response.status === 200) {
          localStorage.setItem('token', response.data.token);
          setIsAuthenticated(true);
          navigate('/app');
          window.location.reload();
        }
      })
      .catch(error => {
        setError({ active: true, message: error.response.data.message });
      });
  };
  const handleLogout = () => {
    localStorage.clear();
    setIsAuthenticated(false);
    navigate('/');
    window.location.reload();
  };

  return { isAuthenticated, setIsAuthenticated, error, setError, handleLogin, handleLogout };
};