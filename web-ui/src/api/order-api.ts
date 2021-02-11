import api from '@/api/api-service';
import { AxiosResponse } from 'axios';

export default {
  async getOrders(page = 1, pageSize = 20, orderId = '', customerId = '', paymentType = '', orderStatus = ''): Promise<AxiosResponse> {
    const qsParams: Record<string, number|string> = { };
    if (page) { qsParams.page = page; }
    if (pageSize) { qsParams['page-size'] = pageSize; }
    if (customerId) { qsParams['customer-id'] = customerId; }
    if (orderId) { qsParams['order-id'] = orderId; }
    if (paymentType) { qsParams['payment-type'] = paymentType; }
    if (orderStatus) { qsParams['order-status'] = orderStatus; }

    return api.get('/orders', { params: qsParams });
  },

  async deleteOrder(orderId: string): Promise<AxiosResponse> {
    return api.delete(`/orders/${orderId}`);
  },

  async deleteOrderLine(orderId: string, productId: string): Promise<AxiosResponse> {
    return api.delete(`/order-item/${orderId}/${productId}`);
  },

  async deleteOrderLines(orderId: string, commaSeparatedProductIds: string): Promise<AxiosResponse> {
    const qsParams: Record<string, unknown> = { };
    if (commaSeparatedProductIds) { qsParams['product-ids'] = commaSeparatedProductIds; }
    return api.delete(`/order-item/${orderId}`, { params: qsParams });
  },
};
