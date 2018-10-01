import axios from 'axios';
import store from '@/store';
import RestUtil from '@/rest/RestUtil';

export default {

  getProducts( page, pageSize, productId, category ){
    let qsParams={};
    if (page)        { qsParams['page']=page;}
    if (pageSize)  { qsParams['page-size']=pageSize;}
    if (productId) { qsParams['id']=productId;}
    if (category)  { qsParams['category']=category;}
    
    return axios.get(RestUtil.getBasePath() + "/products",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  },

  deleteProduct(productId){
    
    return axios.delete(RestUtil.getBasePath() + "/products/" + productId,{
      headers: {'Authorization': RestUtil.getToken()}
    });
  }


}
