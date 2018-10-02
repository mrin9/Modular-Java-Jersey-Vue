import axios from 'axios';
import store from '@/store';
import RestUtil from '@/rest/RestUtil';

export default {

  getOrders(page, pageSize, orderId, customerId, paymentType, orderStatus ){
    let qsParams={};
    if (page)        { qsParams['page']=page;}
    if (pageSize)    { qsParams['page-size']=pageSize;}
    if (customerId)  { qsParams['customer-id']=customerId;}
    if (orderId)     { qsParams['order-id']=orderId;}
    if (paymentType) { qsParams['payment-type']=paymentType;}
    if (orderStatus) { qsParams['order-status']=orderStatus;}
    
    return axios.get(RestUtil.getBasePath() + "/orders",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  },

  deleteOrder(orderId){
    return axios.delete(RestUtil.getBasePath() + "/orders/" + orderId,{
      headers: {'Authorization': RestUtil.getToken()}
    });
  },

  deleteOrderLine(orderId, productId){
    return axios.delete(RestUtil.getBasePath() + "/order-item/" + orderId +"/"+productId,{
      headers: {'Authorization': RestUtil.getToken()}
    });
  },


}
