export interface DashboardStats {
  totalArticles: number;
  trendingTopics: number;
  positiveNews: number;
  negativeNews: number;
  neutralNews: number;
  topCategories: { name: string; count: number }[];
  sentimentData: { name: string; value: number; color: string }[];
  trendingData: { name: string; score: number }[];
  latestHeadlines: { id: string; title: string; sentiment: string; time: string; source: string }[];
}

export const dashboardService = {
  getDashboardData: async (): Promise<DashboardStats> => {
    // TODO: Replace with real API call later
    // return (await api.get('/dashboard')).data;

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          totalArticles: 14592,
          trendingTopics: 8,
          positiveNews: 4200,
          negativeNews: 3100,
          neutralNews: 7292,
          topCategories: [
            { name: 'Technology', count: 420 },
            { name: 'Finance', count: 380 },
            { name: 'Politics', count: 310 },
            { name: 'Science', count: 290 },
          ],
          sentimentData: [
            { name: 'Positive', value: 4200, color: '#10B981' },
            { name: 'Neutral', value: 7292, color: '#6B7280' },
            { name: 'Negative', value: 3100, color: '#EF4444' },
          ],
          trendingData: [
            { name: 'AI Regulation', score: 98 },
            { name: 'SpaceX Launch', score: 85 },
            { name: 'Market Rally', score: 72 },
            { name: 'Quantum Computing', score: 65 },
            { name: 'Election Updates', score: 55 },
          ],
          latestHeadlines: [
            {
              id: '1',
              title: 'OpenAI announces major breakthrough in reasoning capabilities',
              sentiment: 'POSITIVE',
              time: '2 hours ago',
              source: 'TechCrunch',
            },
            {
              id: '2',
              title: 'Global markets dip amid new inflation concerns',
              sentiment: 'NEGATIVE',
              time: '4 hours ago',
              source: 'Reuters',
            },
            {
              id: '3',
              title: 'New electric vehicle battery charges in under 5 minutes',
              sentiment: 'POSITIVE',
              time: '5 hours ago',
              source: 'The Verge',
            },
            {
              id: '4',
              title: 'Senate passes routine infrastructure spending bill',
              sentiment: 'NEUTRAL',
              time: '6 hours ago',
              source: 'AP News',
            },
          ],
        });
      }, 500); // simulate network delay
    });
  },
};
