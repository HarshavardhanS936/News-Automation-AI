import { api } from './api';

export interface NewsArticleMetadata {
  sentiment: 'POSITIVE' | 'NEGATIVE' | 'NEUTRAL';
  sentimentScore: number;
  bias: 'LEFT' | 'RIGHT' | 'CENTER';
  credibilityScore: number;
  readingTimeMinutes: number;
}

export interface NewsArticleData {
  id: number;
  title: string;
  content: string;
  url: string;
  imageUrl?: string;
  publishedAt: string;
  source: string;
  category: string;
  analysis: NewsArticleMetadata;
  bookmarked?: boolean;
}

export interface NewsFilters {
  query?: string;
  category?: string;
  source?: string;
  date?: string;
}

export const newsService = {
  getArticles: async (page: number, filters: NewsFilters, pageSize: number = 20): Promise<{ data: NewsArticleData[], totalPages: number }> => {
    const response = await api.get('/news', {
      params: {
        page: page > 0 ? page - 1 : 0,
        size: pageSize,
        query: filters.query || undefined,
        category: filters.category || undefined,
        source: filters.source || undefined
      }
    });
    
    const backendArticles = response.data.content;
    const data: NewsArticleData[] = backendArticles.map((a: any) => ({
      id: a.id,
      title: a.title,
      content: a.content,
      url: a.url,
      imageUrl: a.imageUrl,
      publishedAt: a.publishedAt,
      source: a.source?.name || 'Unknown',
      category: a.categories && a.categories.length > 0 ? a.categories[0].name : 'General',
      bookmarked: a.bookmarked,
      analysis: {
        sentiment: 'NEUTRAL',
        sentimentScore: 0,
        bias: 'CENTER',
        credibilityScore: 80,
        readingTimeMinutes: 5,
      }
    }));
    
    return { data: data, totalPages: response.data.totalPages };
  },
  
  toggleBookmark: async (articleId: number): Promise<void> => {
    await api.post(`/news/${articleId}/bookmark`);
  },
  
  getCategories: async (): Promise<string[]> => {
    const response = await api.get('/news/categories');
    return response.data;
  },

  getSources: async (): Promise<string[]> => {
    const response = await api.get('/news/sources');
    return response.data;
  },
  
  getSavedArticles: async (): Promise<NewsArticleData[]> => {
    const response = await api.get('/news/bookmarks');
    return response.data.map((a: any) => ({
      id: a.id,
      title: a.title,
      content: a.content,
      url: a.url,
      imageUrl: a.imageUrl,
      publishedAt: a.publishedAt,
      source: a.source?.name || 'Unknown',
      category: a.categories && a.categories.length > 0 ? a.categories[0].name : 'General',
      bookmarked: true,
      analysis: {
        sentiment: 'NEUTRAL',
        sentimentScore: 0,
        bias: 'CENTER',
        credibilityScore: 80,
        readingTimeMinutes: 5,
      }
    }));
  }
};
