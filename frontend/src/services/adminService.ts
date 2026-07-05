import axios from 'axios';

const API_URL =
  import.meta.env.VITE_API_URL || "https://news-automation-ai-1.onrender.com";

const getHeaders = () => {
  const token = localStorage.getItem('token');
  return {
    headers: {
      Authorization: `Bearer ${token}`
    }
  };
};

export const adminService = {
  getUsers: async () => {
    const response = await axios.get(`${API_URL}/users`, getHeaders());
    return response.data;
  },
  
  deleteUser: async (id: number) => {
    await axios.delete(`${API_URL}/users/${id}`, getHeaders());
  },
  
  getArticles: async () => {
    const response = await axios.get(`${API_URL}/articles`, getHeaders());
    return response.data;
  },
  
  deleteArticle: async (id: number) => {
    await axios.delete(`${API_URL}/articles/${id}`, getHeaders());
  },
  
  refreshNews: async () => {
    const response = await axios.post(`${API_URL}/articles/refresh`, {}, getHeaders());
    return response.data;
  },
  
  getSystemStatus: async () => {
    const response = await axios.get(`${API_URL}/system/status`, getHeaders());
    return response.data;
  }
};
