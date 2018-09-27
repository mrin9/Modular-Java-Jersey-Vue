import axios from 'axios';
import store from '@/store';
import RestUtil from '@/rest/RestUtil';

export default {
  login(username, password, domain){
    
    let loginPath = RestUtil.getBasePath() + "/authenticate";

    return axios.post(loginPath+"/user", {username,password,domain}).then(function(resp){
      if (resp.data.data){
        var respData = resp.data.data;
        console.log(respData);
        RestUtil.setToken(respData.token)
        store.commit('serialNumber', respData.serialNumber )
        store.commit('licenseCodes', respData.licenseCodes )
        store.commit('user', respData.username )
        store.commit('role', respData.role )
        store.commit('org' , respData.orgUnit )
        return respData;
      }
      else{
        return resp;
      }
    })
    .catch(function(err){
      console.log("REST ERROR: %O", err.response?err.response:err);
      return Promise.reject(err);
    })
  }

}
