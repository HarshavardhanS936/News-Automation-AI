import React, { useEffect, useState } from 'react';
import { Newspaper, TrendingUp, Smile, AlertCircle } from 'lucide-react';
import { analyticsService, AnalyticsDashboardDto } from '../services/analyticsService';
import StatCard from '../components/dashboard/StatCard';
import SentimentChart from '../components/dashboard/SentimentChart';
import TrendingTopicsChart from '../components/dashboard/TrendingTopicsChart';
import SourceDistributionChart from '../components/dashboard/SourceDistributionChart';
import CategoryDistributionChart from '../components/dashboard/CategoryDistributionChart';
import CredibilityChart from '../components/dashboard/CredibilityChart';
import GrowthChart from '../components/dashboard/GrowthChart';

export default function Home() {
  const [data, setData] = useState<AnalyticsDashboardDto | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const stats = await analyticsService.getDashboardAnalytics();
        
        // Map backend sentiment format to frontend SentimentChart format (color required)
        const mappedSentiment = stats.sentimentDistribution.map(s => {
          let color = '#6B7280';
          if (s.name === 'POSITIVE') color = '#10B981';
          else if (s.name === 'NEGATIVE') color = '#EF4444';
          return { name: s.name, value: s.value, color };
        });
        
        stats.sentimentDistribution = mappedSentiment as any;
        
        setData(stats);
      } catch (err) {
        setError('Failed to load dashboard data. Make sure backend is running.');
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  if (loading) return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 animate-fade-in">
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {[...Array(4)].map((_, i) => (
          <div key={i} className="h-32 bg-white dark:bg-slate-800 rounded-2xl border border-gray-100 dark:border-slate-700 animate-pulse"></div>
        ))}
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 h-[400px] bg-white dark:bg-slate-800 rounded-2xl border border-gray-100 dark:border-slate-700 animate-pulse"></div>
        <div className="h-[400px] bg-white dark:bg-slate-800 rounded-2xl border border-gray-100 dark:border-slate-700 animate-pulse"></div>
      </div>
    </div>
  );

  if (error || !data) return (
    <div className="flex justify-center items-center h-64 text-red-600 dark:text-red-400 font-medium">
      Failed to load dashboard data. Ensure backend is running.
    </div>
  );

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 animate-fade-in">
      {/* Header */}
      <div className="mb-8 flex justify-between items-end">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-900 dark:text-white mb-2">Dashboard Overview</h1>
          <p className="text-gray-500 dark:text-gray-400">Your AI-powered news aggregator insights.</p>
        </div>
      </div>
      
        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <StatCard
            title="Total Articles"
            value={data.totalArticles.toLocaleString()}
            icon={Newspaper}
          />
          <StatCard
            title="Articles Analyzed"
            value={data.totalProcessed.toLocaleString()}
            icon={TrendingUp}
          />
          <StatCard
            title="Positive News"
            value={data.sentimentDistribution.find((s: any) => s.name === 'POSITIVE')?.value || 0}
            icon={Smile}
          />
          <StatCard
            title="Negative News"
            value={data.sentimentDistribution.find((s: any) => s.name === 'NEGATIVE')?.value || 0}
            icon={AlertCircle}
          />
        </div>

        {/* Charts Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
          <SentimentChart data={data.sentimentDistribution as any} />
          <TrendingTopicsChart data={data.trendingTopics.map(t => ({ name: t.name, score: t.value }))} />
          
          <SourceDistributionChart data={data.sourceDistribution} />
          <CategoryDistributionChart data={data.categoryDistribution} />
          
          <CredibilityChart data={data.credibilityAnalysis} />
          
          {/* Growth Chart is full width in its container component */}
          <GrowthChart data={data.dailyArticleGrowth} />
        </div>
    </div>
  );
}
