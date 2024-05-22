import axios from 'axios';
import config from '../../config';

export const handleLoginAction = (data) => {
  return axios.post(`${config.backend}/login`, data)
}