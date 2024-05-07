import axios from 'axios';
import config from '../../../config';

export const fetchJobs  = async () => {
  return await axios.get(`${config.backend}/jobs`)
}
