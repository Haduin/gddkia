import { redirect } from 'react-router-dom';

export const updatePartialState = (setState, partialState) => {
  setState(prevState => ({
    ...prevState,
    ...partialState
  }));
};

export const handleLogout = () => {
  localStorage.clear();
  window.location.reload(true);
  redirect('/');
};