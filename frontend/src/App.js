import React, { createContext, useEffect, useState } from 'react';
import Keycloak from 'keycloak-js';
import axios from 'axios';
import ThemeCustomization from 'themes';
import ScrollTop from 'components/ScrollTop';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import ThemeRoutes from './routes';


const initOptiosn = {
  realm: 'gddkia-realm',
  url: 'http://localhost:8081/',
  clientId: 'gddkia-client-id'
};
const keycloak = new Keycloak(initOptiosn);

keycloak.init({
  onLoad: 'login-required', // Supported values: 'check-sso' , 'login-required'
  checkLoginIframe: true,
  pkceMethod: 'S256'
}).then((auth) => {
  if (!auth) {
    window.location.reload();
  } else {
    axios.interceptors.request.use((config) => {
      config.headers.Authorization = `Bearer ${keycloak.token}`;
      config.headers['Access-Control-Allow-Origin'] = '*';
      return config;
    });

    keycloak.onTokenExpired = () => {
      console.log('token expired');
    };
  }
}, () => {
  /* Notify the user if necessary */
  console.error('Authentication Failed');
});
export const KeyClockContext = createContext({ })

function App() {
  const [keycloakState, setKeycloakState] = useState();
  useEffect(() => {
    setKeycloakState(keycloak);
  }, [keycloak]);

  return (
    <ThemeCustomization>
      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="pl">
        <KeyClockContext.Provider value={keycloakState}>
          <ScrollTop>
            <ThemeRoutes />
          </ScrollTop>
        </KeyClockContext.Provider>
      </LocalizationProvider>
    </ThemeCustomization>
  );
}

export default App;
