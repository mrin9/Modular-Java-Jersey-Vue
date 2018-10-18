import axios from 'axios';
import RestUtil from '@/rest/RestUtil';

export default {


  getDailySale(){
    return axios.get(RestUtil.getBasePath() + "/stats/daily-sale",{
      headers: {'Authorization': RestUtil.getToken()}
    });
  },

  getDailyOrderCount(){
    return axios.get(RestUtil.getBasePath() + "/stats/daily-order-count",{
      headers: {'Authorization': RestUtil.getToken()}
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
