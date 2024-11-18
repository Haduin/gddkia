import axios from 'axios';
import config from '../config';

interface LoginResponse {
  status: 'success' | 'error';
  data: any;
}

export const login_api = async (data: { email: string; password: string }): Promise<LoginResponse> => {
  try {
    const response = await axios.post(`${config.backend}/login`, data);
    return {
      status: 'success',
      data: response.data
    };
  } catch (err) {
    return {
      status: 'error',
      data: err
    };
  }
}
