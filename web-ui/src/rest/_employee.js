import axios from 'axios';
import RestUtil from '@/rest/RestUtil';

export default {

  getEmployees( page, pageSize, employeeId, nameOrEmail, department ){
    let qsParams={};
    if (page)       { qsParams['page']=page;}
    if (pageSize)   { qsParams['page-size']=pageSize;}
    if (employeeId) { qsParams['id']=employeeId;}
    if (nameOrEmail){ qsParams['search']="%"+nameOrEmail+"%";}
    if (department) { qsParams['department']=department;}
    
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
