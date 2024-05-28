import axios from 'axios';
import config from '../../../config';

export const sendNewTer = async (file, companyName, contractNumber, branchName, regionName, sectionName, formattedStartDate, formattedEndDate, roadLength) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('companyName', companyName);
  formData.append('contractName', contractNumber);
  formData.append('dateFrom', formattedStartDate);
  formData.append('dateTo', formattedEndDate);
  formData.append('roadLength', roadLength);
  formData.append('branchName', branchName);
  formData.append('regionName', regionName);
  formData.append('sectionName', sectionName);
  return await axios.post(`${config.backend}/estimate/upload`, formData);
};