import axios from 'axios';
import store from '@/store';
import RestUtil from '@/rest/RestUtil';

export default {

  getOrder( customerId ){
    let qsParams={};
    if (userId){
      qsParams['customer-id']=customerId;
    }
    
    return axios.get(RestUtil.getBasePath() + "/orders",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  }

}
