import api from '@/api/api-service';
import { AxiosResponse } from 'axios';

export default {
  async getCustomers(page = 1, pageSize = 20, customerId = '', name = '', company = ''): Promise<AxiosResponse> {
    const qsParams: Record<string, unknown> = { };
    if (customerId) { qsParams.id = customerId; }
    if (page) { qsParams.page = page; }
    if (pageSize) { qsParams['page-size'] = pageSize; }
    if (name) { qsParams.name = `%${name}%`; }
    if (company) { qsParams.company = company; }

    return api.get('/customers', { params: qsParams });
  },

  async deleteCustomer(customerId: string): Promise<AxiosResponse> {
    return api.delete(`/customers/${customerId}`);
  },

  async addCustomer(customerObj: Record<string, unknown>): Promise<AxiosResponse> {
    return api.post('/customers', customerObj);
  },

  async updateCustomer(customerObj: Record<string, unknown>): Promise<AxiosResponse> {
    return api.put('/customers', customerObj);
  },
};
