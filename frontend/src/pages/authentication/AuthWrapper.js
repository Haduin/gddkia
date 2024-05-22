import PropTypes from 'prop-types';

// material-ui
import { Alert, Box, Button, Grid } from '@mui/material';

// project import
import AuthCard from './AuthCard';
import AuthFooter from 'components/cards/AuthFooter';
import { useAuthentication } from '../../hooks/useAuthentication';

// assets

// ==============================|| AUTHENTICATION - WRAPPER ||============================== //

const AuthWrapper = ({ children }) => {
  const { error, setError } = useAuthentication();
  const closeErrorAlert = () => {
    setError({ active: false, message: '' });
  };

  return (
    <Box sx={{ minHeight: '100vh' }}>
      <Grid
        container
        direction="column"
        justifyContent="flex-end"
        sx={{
          minHeight: '100vh'
        }}
      >
        {error.active ? (
          <Grid container
                justifyContent="center"
                xs={12}
                alignItems="center" item>
            <Alert variant="filled" severity="error"
                   action={<Button onClick={closeErrorAlert} color="primary" size="small">Zamknij </Button>}>
              {error.message}
            </Alert>
          </Grid>
        ) : <></>}
        <Grid item xs={12}>
          <Grid
            item
            xs={12}
            container
            justifyContent="center"
            alignItems="center"
            sx={{ minHeight: { xs: 'calc(100vh - 134px)', md: 'calc(100vh - 112px)' } }}
          >
            <Grid item>
              <AuthCard>{children}</AuthCard>
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12} sx={{ m: 3, mt: 1 }}>
          <AuthFooter />
        </Grid>
      </Grid>
    </Box>
  );
};

AuthWrapper.propTypes = {
  children: PropTypes.node
};

export default AuthWrapper;
