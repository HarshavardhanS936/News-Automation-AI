import { api } from './api';

export const adminService = {
  getUsers: async () => {
    const response = await api.get(`/admin/users`);
    return response.data;
  },
  
  deleteUser: async (id: number) => {
    await api.delete(`/admin/users/${id}`);
  },
  
  getArticles: async () => {
    const response = await api.get(`/admin/articles`);
    return response.data;
  },
  
  deleteArticle: async (id: number) => {
    await api.delete(`/admin/articles/${id}`);
  },
  
  refreshNews: async () => {
    const response = await api.post(`/admin/articles/refresh`, {});
    return response.data;
  },
  
  getSystemStatus: async () => {
    const response = await api.get(`/admin/system/status`);
    return response.data;
  }
};
