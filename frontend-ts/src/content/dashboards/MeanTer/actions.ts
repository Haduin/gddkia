import axios from 'axios';
import config from '../../../config';

export const fetchJobs = async (
  startDate: string | null,
  endDate: string | null,
  selectedBranch: string,
  selectedRegion: string[],
  selectedSection: string[]
): Promise<any> => {
  const requestBody = {
    startDate,
    endDate,
    selectedBranch,
    selectedRegion,
    selectedSection
  };
  return axios.post(`${config.backend}/jobs`, requestBody);
};
