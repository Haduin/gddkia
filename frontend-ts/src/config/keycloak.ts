import Keycloak from 'keycloak-js';
import axios from 'axios';

const initOptions = {
  realm: 'gddkia-realm',
  url: 'http://localhost:8081/',
  clientId: 'gddkia-client-id'
};

const keycloak = new Keycloak(initOptions);

export const initializeKeycloak = () => 
  keycloak.init({
    onLoad: 'login-required',
    checkLoginIframe: true,
    pkceMethod: 'S256'
  }).then((auth) => {
    if (!auth) {
      window.location.reload();
    } else {
      axios.interceptors.request.use((config) => {
        if (config.headers) {
          config.headers.Authorization = `Bearer ${keycloak.token}`;
          config.headers['Access-Control-Allow-Origin'] = '*';
        }
        return config;
      });

      keycloak.onTokenExpired = () => {
        console.log('token expired');
      };
    }
    return auth;
  }).catch(() => {
    console.error('Authentication Failed');
  });

export default keycloak;
