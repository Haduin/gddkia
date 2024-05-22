import { useAuthentication } from '../hooks/useAuthentication';
import { useNavigate } from 'react-router-dom';

export const ProtectedRoute = ({children}) => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuthentication();
  if (!isAuthenticated) {
    navigate("/login");
  }
  return (
    {children}
  );
};
