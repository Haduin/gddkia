import axios from 'axios';
import config from '../../../config';

export const sendNewTer = async (
  file: File,
  companyName: string,
  contractNumber: string, 
  branchName: string,
  regionName: string[],
  sectionName: string[],
  startDate: string | null,
  endDate: string | null,
  roadLength: number
): Promise<any> => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('companyName', companyName);
  formData.append('contractName', contractNumber);
  formData.append('dateFrom', startDate || '');
  formData.append('dateTo', endDate || '');
  formData.append('roadLength', roadLength.toString());
  formData.append('branchName', branchName);
  formData.append('regionName', regionName.join(','));
  formData.append('sectionName', sectionName.join(','));
  
  return axios.post(`${config.backend}/estimate/upload`, formData);
};
