import axios from 'axios';

export interface ChartData {
  name: string;
  value: number;
}

export interface AnalyticsDashboardDto {
  totalArticles: number;
  totalProcessed: number;
  sentimentDistribution: ChartData[];
  trendingTopics: ChartData[];
  sourceDistribution: ChartData[];
  categoryDistribution: ChartData[];
  credibilityAnalysis: ChartData[];
  dailyArticleGrowth: ChartData[];
}

const API_URL = 'http://localhost:8080/api/v1/analytics';

export const analyticsService = {
  getDashboardAnalytics: async (): Promise<AnalyticsDashboardDto> => {
    // In a real app, you'd use your configured axios instance with JWT interceptors.
    // Assuming you have an api.ts, you would import api from './api' and use api.get(...)
    const token = localStorage.getItem('token');
    const response = await axios.get(`${API_URL}/dashboard`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  }
};
