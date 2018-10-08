import axios from 'axios';
import RestUtil from '@/rest/RestUtil';

export default {

  getCustomers( page, pageSize, customerId, firstName, company ){
    let qsParams={};
    if (page)       { qsParams['page']=page;}
    if (pageSize)   { qsParams['page-size']=pageSize;}
    if (customerId) { qsParams['id']=customerId;}
    if (firstName)  { qsParams['first-name']="%"+firstName+"%";}
    if (company)    { qsParams['company']=company;}
    
    return axios.get(RestUtil.getBasePath() + "/customers",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  },

  deleteCustomer(customerId){
    return axios.delete(RestUtil.getBasePath() + "/customers/" + customerId,{
      headers: {'Authorization': RestUtil.getToken()}
    });
  },

  addCustomer(customerObj){
    return axios.post(RestUtil.getBasePath() + "/customers", customerObj, {
      headers: {'Authorization': RestUtil.getToken()}
    });
  },

  updateCustomer(customerObj){
    return axios.put(RestUtil.getBasePath() + "/customers", customerObj, {
      headers: {'Authorization': RestUtil.getToken()}
    });
  }

}
