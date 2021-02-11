import api from '@/api/api-service';
import { AxiosResponse } from 'axios';

export default {
  async getCartItems(userId: string): Promise<AxiosResponse> {
    const qsParams: Record<string, string> = {
      'user-id': userId || '',
    };
    return api.get('/cart', { params: qsParams });
  },
};
