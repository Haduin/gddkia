import { FC, createContext, useState, useEffect } from 'react';
import { User } from '../hooks/useUser';

type AuthContextType = {
  user: User | null;
  setUser: (user: User | null) => void;
  isInitialized: boolean;
};

export const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export const AuthProvider: FC = ({ children }) => {
  const [isInitialized, setIsInitialized] = useState(false);
  const [user, setUser] = useState<User | null>({
    email: 'dev@example.com',
    authToken: 'dev-mode'
  });

  useEffect(() => {
    // W trybie developerskim zawsze jesteśmy zalogowani
    setIsInitialized(true);
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user,
        setUser,
        isInitialized
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
