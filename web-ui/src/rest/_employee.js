import axios from 'axios';
import RestUtil from '@/rest/RestUtil';

export default {

  getProducts( page, pageSize, productId, category ){
    let qsParams={};
    if (page)      { qsParams['page']=page;}
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
  },

  addProduct(productObj){
    return axios.post(RestUtil.getBasePath() + "/products", productObj, {
      headers: {'Authorization': RestUtil.getToken()}
    });
  },

  updateProduct(productObj){
    return axios.put(RestUtil.getBasePath() + "/products", productObj, {
      headers: {'Authorization': RestUtil.getToken()}
    });
  }

}
