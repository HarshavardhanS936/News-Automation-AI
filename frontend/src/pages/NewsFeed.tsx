import React, { useState, useEffect, useRef, useCallback } from 'react';
import NewsFiltersComponent from '../components/news/NewsFilters';
import NewsCard from '../components/news/NewsCard';
import { newsService, NewsArticleData, NewsFilters as FilterType } from '../services/newsService';
import { Loader2 } from 'lucide-react';
import SkeletonLoader from '../components/ui/SkeletonLoader';
import EmptyState from '../components/ui/EmptyState';
import toast from 'react-hot-toast';

export default function NewsFeed() {
  const [articles, setArticles] = useState<NewsArticleData[]>([]);
  const [filters, setFilters] = useState<FilterType>({});
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);

  // When filters change, reset page and articles
  useEffect(() => {
    setPage(1);
    setArticles([]);
    setHasMore(true);
    // Let the other useEffect handle fetching based on page 1
  }, [filters]);

  useEffect(() => {
    if (!hasMore) return;

    const fetchArticles = async () => {
      setLoading(true);
      try {
        const response = await newsService.getArticles(page, filters);
        setArticles(prev => {
          // Prevent duplicates if StrictMode double fires
          const newIds = new Set(response.data.map(a => a.id));
          const filteredPrev = prev.filter(a => !newIds.has(a.id));
          return [...filteredPrev, ...response.data];
        });
        setHasMore(page < response.totalPages);
      } catch (err) {
        toast.error("Failed to fetch news. Please check your connection.");
        console.error("Failed to fetch news", err);
      } finally {
        setLoading(false);
      }
    };

    fetchArticles();
  }, [page, filters]); // include filters to fetch on reset

  // Infinite scroll implementation using IntersectionObserver
  const observer = useRef<IntersectionObserver | null>(null);
  const lastElementRef = useCallback((node: HTMLDivElement) => {
    if (loading) return;
    if (observer.current) observer.current.disconnect();

    observer.current = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting && hasMore) {
        setPage(prevPage => prevPage + 1);
      }
    });

    if (node) observer.current.observe(node);
  }, [loading, hasMore]);

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">News Feed</h1>
        <h1 className="text-3xl font-extrabold text-gray-900 dark:text-white mb-2">Latest News</h1>
        <p className="text-gray-500 dark:text-gray-400">Your personalized, AI-curated news feed.</p>
      </div>

      <NewsFiltersComponent filters={filters} setFilters={setFilters} />

      {articles.length === 0 && !loading && (
        <EmptyState 
          message="No articles found" 
          subMessage="Try adjusting your filters or search query to find what you're looking for." 
        />
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        {articles.map((article, index) => {
          if (articles.length === index + 1) {
            return (
              <div ref={lastElementRef} key={article.id}>
                <NewsCard article={article} />
              </div>
            );
          } else {
            return <NewsCard key={article.id} article={article} />;
          }
        })}
        {loading && (
          <>
            <SkeletonLoader />
            <SkeletonLoader />
            <SkeletonLoader />
          </>
        )}
      </div>

      {!hasMore && articles.length > 0 && (
        <div className="text-center py-8">
          <p className="text-gray-500 dark:text-gray-400">You've reached the end of the feed.</p>
        </div>
      )}
    </div>
  );
}
