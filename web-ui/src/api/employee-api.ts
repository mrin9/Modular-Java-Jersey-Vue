import api from '@/api/api-service';
import { AxiosResponse } from 'axios';

export default {
  async getEmployees(page = 1, pageSize = 20, employeeId = '', nameOrEmail = '', department = ''): Promise<AxiosResponse> {
    const qsParams: Record<string, number|string> = { };
    if (employeeId) { qsParams.id = employeeId; }
    if (page) { qsParams.page = page; }
    if (pageSize) { qsParams['page-size'] = pageSize; }
    if (nameOrEmail) { qsParams.search = `%${nameOrEmail}%`; }
    if (department) { qsParams.department = department; }

    return api.get('/employees', { params: qsParams });
  },

  async deleteEmployee(employeeId: string): Promise<AxiosResponse> {
    return api.delete(`/employees/${employeeId}`);
  },

  async addEmployee(employeeObj: Record<string, string|number>): Promise<AxiosResponse> {
    return api.post('/employees', employeeObj);
  },

  async updateEmployee(employeeObj: Record<string, string|number>): Promise<AxiosResponse> {
    return api.put('/employees', employeeObj);
  },
};
