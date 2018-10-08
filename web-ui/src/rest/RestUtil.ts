import store from '@/store';
import axios, {
  AxiosRequestConfig,
  AxiosResponse,
  AxiosError,
  AxiosInstance,
  AxiosAdapter,
  Cancel,
  CancelToken,
  CancelTokenSource,
  Canceler
} from 'axios';
let tokenKey = "jwt";
let restServiceRoot = "/api";

const RestUtil:any =  {
enableMock:false,
pingServer:false,
ajaxTimeout:150000,

getToken(){
  return  store.state.jwt;
},

setToken(tokenValue:string){
  store.commit(tokenKey, tokenValue)
},

getBasePath(){
  //return window.location.origin + restServiceRoot;
  return "http://localhost:8080" + restServiceRoot;
},

globalErrorParser(err:AxiosError){
  let me:any = this;

  if (err.code === 'ECONNABORTED') {
    console.log(`A timeout happend on url ${err.config.url}`);
    me.$alert('Unable to connect', 'Request Timedout', {confirmButtonText: 'OK',});
    return Promise.reject(err);
  }
  console.log("SW Global Error Interceptor: %O",err);

  if (err.response != undefined){
    if (err.response.status===400){
      me.$alert('Unable to Process the request', 'Error', {
        confirmButtonText: 'OK',
      });
    }
    if (err.response.status===401 && (me.alertShown === undefined || me.alertShown===false )){
      
      me.alertShown=true;
      setTimeout(function(){ me.alertShown=false; }, 3000);
      me.$alert('Please Login Again', 'Session Expired', {confirmButtonText: 'OK',});
      return;
    }
    if (err.response.status===403){
      me.$alert('You do not have access to perform this operation', 'Access Denied', {
        confirmButtonText: 'OK',
      });
      return;
    }
    if (err.response.status===500){
      me.$alert('Internal Server Error', 'Error', {
        confirmButtonText: 'OK',
      });
      return Promise.reject(err);
    }

  }
  return Promise.reject(err);
},

globalSuccessParser(resp:AxiosResponse){
  return resp;
}


}

export default RestUtil;