import axios from 'axios';
import store from '@/store';
import RestUtil from '@/rest/RestUtil';

export default {

  getOrder( page, pageSize, orderId, customerId, paymentType, orderStatus ){
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
  }

}
