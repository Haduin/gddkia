import { useRoutes } from 'react-router-dom';
import { useEffect, useState } from 'react';
import router from 'src/router';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import { CssBaseline } from '@mui/material';
import ThemeProvider from './theme/ThemeProvider';
import { AuthProvider } from './contexts/AuthContext';
import { KeycloakContext } from './contexts/KeycloakContext';
import keycloak, { initializeKeycloak } from './config/keycloak';

function App() {
  const content = useRoutes(router);
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    initializeKeycloak().then(() => {
      setInitialized(true);
    });
  }, []);

  if (!initialized) {
    return <div>Loading...</div>;
  }

  return (
    <KeycloakContext.Provider value={keycloak}>
      <AuthProvider>
        <ThemeProvider>
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <CssBaseline />
            {content}
          </LocalizationProvider>
        </ThemeProvider>
      </AuthProvider>
    </KeycloakContext.Provider>
  );
}

export default App;
