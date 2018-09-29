import axios from 'axios';
import store from '@/store';
import RestUtil from '@/rest/RestUtil';

export default {


  getCartItems( userId ){
    let qsParams = {'user-id':userId};
    
    return axios.get(RestUtil.getBasePath() + "/cart",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  }

}
