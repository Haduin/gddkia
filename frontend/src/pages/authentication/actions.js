import axios from 'axios';
import config from '../../config';

export const handleLoginAction = async (data) => {
  return await axios.post(`${config.backend}/login`, data)
}