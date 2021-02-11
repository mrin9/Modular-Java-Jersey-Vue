import store from '@/store';
import { AppEvent } from '@/main';
import axios, { AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';

export function getBasePath(): string {
  const basePath = `${store.getters.baseUrl.replace(/\/$/, '')}/api`;
  return basePath;
}
const apiRequestTimeout = 180000;

const browserTimezone: string = Intl
  ? Intl.DateTimeFormat()
    .resolvedOptions()
    .timeZone
  : 'utc';

function createAxiosApiService() {
  return axios.create({
    baseURL: getBasePath(),
    timeout: apiRequestTimeout,
  });
}

function errorResponseParser(err: AxiosError): Promise<AxiosResponse> {
  console.error('[ES] - Error Interceptor: %o', err.response ? err.response : err);
  if (err.response) {
    if (err.response.status === 401) {
      if (err.response.data && err.response.data.msgType === 'AUTH_FAILED') {
        store.commit('jwt', '');
        store.commit('jwtTime', '');
        AppEvent.emit('auth-failed', { msg: 'Incorrect username or password' });
      } else if (err.response.data && err.response.data.msgType === 'NO_ACCESS') {
        AppEvent.emit('no-access', { msg: 'Operation is not allowed for the given role' });
      } else if (err.response.data && err.response.data.msgType === 'BAD_TOKEN') {
        AppEvent.emit('bad-token', { msg: 'Session expired or incorrect token' });
      }
    } else {
      // AppEvent.emit('server-access-error');
      // TODO : Error Dialog (`Unable to Process the request (${err.response.status})`)
    }
    return Promise.reject(err);
  }
  // If there is no response then check if its a timeout issue
  if (err.code === 'ECONNABORTED') {
    // TODO : Error Dialog (Timeout)
  }

  // For all other cases show error
  // TODO : Error Dialog
  return Promise.reject(err);
}

export default {
  async get(path: string, requestConfig: AxiosRequestConfig = {}): Promise<AxiosResponse> {
    const apiService = createAxiosApiService();
    let axiosConfig = { ...requestConfig };
    const headers = {
      headers: {
        Authorization: store.getters.jwt,
        'timezone-id': browserTimezone,
      },
    };
    if (requestConfig.headers && requestConfig.headers instanceof Object) {
      axiosConfig.headers = Object.assign(requestConfig.headers, headers.headers);
    } else {
      axiosConfig = Object.assign(requestConfig, headers);
    }
    try {
      const resp = await apiService.get(path.replace(/^\/|\/$/g, ''), axiosConfig);
      return resp;
    } catch (err) {
      return errorResponseParser(err);
    }
  },

  async put(path: string, putData: unknown, requestConfig: AxiosRequestConfig = {}): Promise<AxiosResponse> {
    // return Promise.reject('HTTP Method is not allowed');
    const apiService = createAxiosApiService();
    let axiosConfig = { ...requestConfig };
    const headers = {
      headers: {
        Authorization: store.getters.jwt,
        'timezone-id': browserTimezone,
      },
    };

    if (requestConfig.headers && requestConfig.headers instanceof Object) {
      axiosConfig.headers = Object.assign(requestConfig.headers, headers.headers);
    } else {
      axiosConfig = Object.assign(requestConfig, headers);
    }
    try {
      const resp = await apiService.put(path.replace(/^\/|\/$/g, ''), putData, axiosConfig);
      return resp;
    } catch (err) {
      return errorResponseParser(err);
    }
  },

  async post(path: string, postData: unknown, requestConfig: AxiosRequestConfig = {}): Promise<AxiosResponse> {
    const apiService = createAxiosApiService();
    let axiosConfig = { ...requestConfig };
    const headers = {
      headers: {
        Authorization: store.getters.jwt,
        'timezone-id': browserTimezone,
      },
    };

    if (requestConfig?.headers instanceof Object) {
      axiosConfig.headers = Object.assign(requestConfig.headers, headers.headers);
    } else {
      axiosConfig = Object.assign(requestConfig, headers);
    }

    if (requestConfig?.headers instanceof Object) {
      axiosConfig.headers = Object.assign(requestConfig.headers, headers.headers);
    } else {
      axiosConfig = Object.assign(requestConfig, headers);
    }
    try {
      const resp = await apiService.post(path.replace(/^\/|\/$/g, ''), postData, axiosConfig);
      return resp;
    } catch (err) {
      return errorResponseParser(err);
    }
  },

  async delete(path: string, requestConfig: AxiosRequestConfig = {}): Promise<AxiosResponse> {
    const apiService = createAxiosApiService();
    let axiosConfig = { ...requestConfig };
    const headers = {
      headers: {
        Authorization: store.getters.jwt,
        'timezone-id': browserTimezone,
      },
    };

    if (requestConfig?.headers instanceof Object) {
      axiosConfig.headers = Object.assign(requestConfig.headers, headers.headers);
    } else {
      axiosConfig = Object.assign(requestConfig, headers);
    }

    try {
      const resp = await apiService.delete(path.replace(/^\/|\/$/g, ''), axiosConfig);
      return resp;
    } catch (err) {
      return errorResponseParser(err);
    }
  },
};
