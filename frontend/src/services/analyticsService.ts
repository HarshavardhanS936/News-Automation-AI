import { api } from './api';

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

export const analyticsService = {
  getDashboardAnalytics: async (): Promise<AnalyticsDashboardDto> => {
    const response = await api.get('/analytics/dashboard');
    return response.data;
  }
};
