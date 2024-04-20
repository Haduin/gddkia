import axios from 'axios';

export const sendNewTer = (file, companyName, contractNumber, regionName, branchName) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('companyName', companyName);
  formData.append('contractName', contractNumber);
  formData.append('dateFrom', '10/10/2022');
  formData.append('dateTo', '10/10/2022');
  formData.append('regionName', regionName);
  formData.append('branchName', branchName);
  return axios.post('/estimate/upload', formData)
}