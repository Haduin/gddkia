import { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import { login_api } from '../api/auth';

interface LoginCredentials {
  email: string;
  password: string;
}

interface AuthError {
  active: boolean;
  message: string;
}

export const useAuthentication = () => {
  const navigate = useNavigate();
  const { setUser } = useContext(AuthContext);
  const [error, setError] = useState<AuthError>({ active: false, message: '' });

  const handleLogin = async (credentials: LoginCredentials) => {
    try {
      const result = await login_api(credentials);
      if (result.status === 'success') {
        setUser({
          email: credentials.email,
          authToken: result.data.accessToken
        });
        navigate('/overview');
      } else {
        setError({
          active: true,
          message: 'Login failed'
        });
      }
    } catch (err) {
      setError({
        active: true,
        message: 'Login failed'
      });
    }
  };

  const handleLogout = () => {
    setUser(null);
    navigate('/login');
  };

  return {
    error,
    setError,
    handleLogin,
    handleLogout
  };
};
