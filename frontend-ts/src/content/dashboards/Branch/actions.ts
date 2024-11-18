import axios from 'axios';
import config from '../../../config';

export const fetchBranch = async (): Promise<any> => {
  return axios.get(`${config.backend}/branch`);
};
