import axios from 'axios';
import config from '../../../config';

export const fetchJobs = async (startDate, endDate, selectedBranch, selectedRegion, selectedSection) => {
  const requestBody = {
    startDate: startDate,
    endDate: endDate,
    selectedBranch: selectedBranch,
    selectedRegion: selectedRegion,
    selectedSection: selectedSection
  }
  return await axios.post(`${config.backend}/jobs`, requestBody);
};
