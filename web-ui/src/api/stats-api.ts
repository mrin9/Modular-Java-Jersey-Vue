import api from '@/api/api-service';
import { AxiosResponse } from 'axios';

export default {
  async getDailySale(): Promise<AxiosResponse> {
    return api.get('/stats/daily-sale');
  },

  async getDailyOrderCount(): Promise<AxiosResponse> {
    return api.get('/stats/daily-order-count');
  },

  async getOrdersStats(groupBy: 'by-status' | 'by-payment-type'): Promise<AxiosResponse> {
    if (groupBy === 'by-status') {
      return api.get('/stats/orders-by-status');
    }
    return api.get('/stats/orders-by-payment-type');
  },
};
