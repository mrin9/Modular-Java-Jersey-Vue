import axios from 'axios';
import RestUtil from '@/rest/RestUtil';

export default {


  getDailySale(){
    let qsParams={};
    
    return axios.get(RestUtil.getBasePath() + "/stats/daily-sale",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  }

}
