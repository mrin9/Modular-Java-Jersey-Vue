import api from '@/api/api-service';
import { AxiosResponse } from 'axios';

export default {
  async getProducts(page = 1, pageSize = 20, productId = '', category = ''): Promise<AxiosResponse> {
    const qsParams: Record<string, number|string> = { };
    if (page) { qsParams.page = page; }
    if (pageSize) { qsParams['page-size'] = pageSize; }
    if (productId) { qsParams.id = productId; }
    if (category) { qsParams.category = category; }

    return api.get('/products', { params: qsParams });
  },

  async deleteProduct(productId: string): Promise<AxiosResponse> {
    return api.delete(`/products/${productId}`);
  },

  async addProduct(productObj: Record<string, string|number>): Promise<AxiosResponse> {
    return api.post('/products', productObj);
  },

  async updateProduct(productObj: Record<string, string|number>): Promise<AxiosResponse> {
    return api.put('/products', productObj);
  },
};
