import axios from 'axios';
import config from '../../../config';

export const sendNewTer = (file, companyName, contractNumber, regionName, branchName) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('companyName', companyName);
  formData.append('contractName', contractNumber);
  formData.append('dateFrom', '10/10/2022');
  formData.append('dateTo', '10/10/2022');
  formData.append('regionName', regionName);
  formData.append('branchName', branchName);
  axios.post(config.backend + '/estimate/upload', formData).then(response => console.log(response));
}