import React, { useState, useEffect } from 'react';
import { Bookmark, Share2, ExternalLink, Clock, ShieldCheck, Scale, MessageCircle, TrendingUp } from 'lucide-react';
import { NewsArticleData, newsService } from '../../services/newsService';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

interface NewsCardProps {
  article: NewsArticleData;
}

export default function NewsCard({ article }: { article: NewsArticleData }) {
  const [isBookmarked, setIsBookmarked] = useState(article.bookmarked || false);
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    setIsBookmarked(article.bookmarked || false);
  }, [article.bookmarked]);

  const getSentimentColor = (score: number) => {
    if (score >= 0.5) return 'text-green-600 dark:text-green-400 bg-green-50 dark:bg-green-900/30';
    if (score <= -0.5) return 'text-red-600 dark:text-red-400 bg-red-50 dark:bg-red-900/30';
    return 'text-gray-600 dark:text-gray-400 bg-gray-50 dark:bg-gray-800';
  };

  const handleBookmark = async () => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }
    
    setIsBookmarked(!isBookmarked);
    try {
      await newsService.toggleBookmark(article.id);
    } catch (error) {
      setIsBookmarked(isBookmarked);
      console.error("Failed to toggle bookmark", error);
    }
  };

  return (
    <article className="glass-card rounded-2xl overflow-hidden flex flex-col h-full animate-slide-up group">
      {article.imageUrl ? (
        <div className="relative h-48 overflow-hidden">
          <img 
            src={article.imageUrl} 
            alt={article.title}
            className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
            loading="lazy"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
          <div className="absolute top-3 left-3 flex space-x-2">
            <span className={`px-2.5 py-1 rounded-full text-xs font-semibold border shadow-sm backdrop-blur-sm bg-white/90 ${getSentimentColor(article.analysis.sentimentScore || 0)}`}>
              <MessageCircle className="w-3 h-3 inline mr-1" />
              {article.analysis.sentiment}
            </span>
          </div>
        </div>
      ) : (
        <div className="h-4 bg-blue-600 w-full"></div>
      )}

      <div className="p-5 flex-grow flex flex-col">
        <div className="flex justify-between items-start mb-3">
          <span className="inline-block px-3 py-1 bg-blue-50 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400 text-xs font-semibold rounded-full">
            {article.source}
          </span>
          <span className="text-xs text-gray-500 dark:text-gray-400 flex items-center">
            <Clock className="w-3 h-3 mr-1" />
            {new Date(article.publishedAt).toLocaleDateString()}
          </span>
        </div>
        
        <h2 className="text-lg font-bold text-gray-900 dark:text-white mb-2 line-clamp-2 hover:text-blue-600 dark:hover:text-blue-400 transition-colors">
          <a href={article.url} target="_blank" rel="noopener noreferrer">
            {article.title}
          </a>
        </h2>
        
        <div className="grid grid-cols-2 gap-3 mb-4 mt-auto">
          <div className="flex items-center text-sm text-gray-600 dark:text-gray-400">
            <Scale className="w-4 h-4 mr-2 text-gray-400" />
            <span className="font-medium">Bias: </span>
            <span className="ml-1 capitalize">{article.analysis.bias.toLowerCase()}</span>
          </div>
          <div className="flex items-center text-sm text-gray-600 dark:text-gray-400">
            <ShieldCheck className="w-4 h-4 mr-2 text-blue-400" />
            <span className="font-medium">Credibility: </span>
            <span className="ml-1">{article.analysis.credibilityScore}%</span>
          </div>
        </div>

        <div className="flex items-center justify-between mt-auto pt-4 border-t border-gray-100 dark:border-slate-700/50">
          <div className="flex items-center space-x-2">
            <div className={`flex items-center px-2 py-1 rounded-md text-xs font-medium ${getSentimentColor(article.analysis?.sentimentScore || 0)}`}>
              <TrendingUp className="w-3 h-3 mr-1" />
              {((article.analysis?.sentimentScore || 0) * 100).toFixed(0)}% Sentiment
            </div>
          </div>
          
          <div className="flex items-center space-x-2">
            <button 
              onClick={handleBookmark}
              className={`p-2 rounded-full transition-colors ${isBookmarked ? 'bg-blue-50 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400' : 'text-gray-400 dark:text-gray-500 hover:bg-gray-50 dark:hover:bg-slate-700 hover:text-gray-600 dark:hover:text-gray-300'}`}
            >
              <Bookmark className={`w-5 h-5 ${isBookmarked ? 'fill-current' : ''}`} />
            </button>
            <button className="p-2 rounded-full text-gray-400 dark:text-gray-500 hover:bg-gray-50 dark:hover:bg-slate-700 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
              <Share2 className="w-5 h-5" />
            </button>
          </div>
        </div>
      </div>
    </article>
  );
}
