import React, { useEffect, useState } from 'react';
import { newsService, NewsArticleData } from '../services/newsService';
import NewsCard from '../components/news/NewsCard';
import { Bookmark } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { Navigate } from 'react-router-dom';

export default function SavedArticles() {
  const { isAuthenticated } = useAuth();
  const [articles, setArticles] = useState<NewsArticleData[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAuthenticated) return;
    const fetchSaved = async () => {
      try {
        const saved = await newsService.getSavedArticles();
        setArticles(saved);
      } catch (error) {
        console.error("Failed to fetch saved articles", error);
      } finally {
        setLoading(false);
      }
    };
    fetchSaved();
  }, [isAuthenticated]);

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="flex items-center space-x-3 mb-8">
        <Bookmark className="w-8 h-8 text-blue-600 dark:text-blue-400" />
        <h1 className="text-3xl font-bold text-gray-900 dark:text-white">Saved Articles</h1>
      </div>

      {loading ? (
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
        </div>
      ) : articles.length === 0 ? (
        <div className="text-center py-20 bg-white/50 dark:bg-slate-900/50 rounded-2xl border border-gray-200 dark:border-slate-800 backdrop-blur-md">
          <Bookmark className="w-16 h-16 text-gray-400 mx-auto mb-4 opacity-50" />
          <h2 className="text-xl font-medium text-gray-900 dark:text-white mb-2">No saved articles yet</h2>
          <p className="text-gray-500 dark:text-gray-400">Articles you bookmark will appear here for easy reading later.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {articles.map((article) => (
            <NewsCard key={article.id} article={article} />
          ))}
        </div>
      )}
    </div>
  );
}
