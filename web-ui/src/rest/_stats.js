import axios from 'axios';
import RestUtil from '@/rest/RestUtil';

export default {


  getDailySale(){
    let qsParams={};
    return axios.get(RestUtil.getBasePath() + "/stats/daily-sale",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  },

  getOrdersStats( groupBy ){
    if (groupBy==="by-status"){
      return axios.get(RestUtil.getBasePath() + "/stats/orders-by-status",{ 
        headers: {'Authorization': RestUtil.getToken()}  
      });
    }
    else{
      return axios.get(RestUtil.getBasePath() + "/stats/orders-by-payment-type",{ 
        headers: {'Authorization': RestUtil.getToken()}  
      });
    }
  }





}
