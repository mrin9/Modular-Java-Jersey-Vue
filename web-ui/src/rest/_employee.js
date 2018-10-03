import axios from 'axios';
import RestUtil from '@/rest/RestUtil';

export default {

  getEmployees( page, pageSize, employeeId, firstName, department ){
    let qsParams={};
    if (page)       { qsParams['page']=page;}
    if (pageSize)   { qsParams['page-size']=pageSize;}
    if (employeeId) { qsParams['id']=employeeId;}
    if (firstName)  { qsParams['first-name']=firstName;}
    if (department) { qsParams['id']=department;}
    
    return axios.get(RestUtil.getBasePath() + "/employees",{
      headers: {'Authorization': RestUtil.getToken()},
      params:qsParams
    });
  },

  deleteEmployee(employeeId){
    return axios.delete(RestUtil.getBasePath() + "/employees/" + employeeId,{
      headers: {'Authorization': RestUtil.getToken()}
    });
  },

  addEmployee(employeeObj){
    return axios.post(RestUtil.getBasePath() + "/employees", employeeObj, {
      headers: {'Authorization': RestUtil.getToken()}
    });
  },

  updateEmployee(employeeObj){
    return axios.put(RestUtil.getBasePath() + "/employees", employeeObj, {
      headers: {'Authorization': RestUtil.getToken()}
    });
  }

}
