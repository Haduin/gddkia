import axios from 'axios';
import config from '../../../config';

export const fetchBranch  = async () => {
  return await axios.get(`${config.backend}/branch`)
}
