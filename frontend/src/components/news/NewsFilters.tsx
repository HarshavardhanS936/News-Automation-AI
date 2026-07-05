import React, { useState, useEffect } from 'react';
import { Search, Filter } from 'lucide-react';
import { NewsFilters as FilterType, newsService } from '../../services/newsService';

interface NewsFiltersProps {
  filters: FilterType;
  setFilters: React.Dispatch<React.SetStateAction<FilterType>>;
}

export default function NewsFilters({ filters, setFilters }: NewsFiltersProps) {
  
  const [categories, setCategories] = useState<string[]>([]);
  const [sources, setSources] = useState<string[]>([]);

  useEffect(() => {
    const fetchFilterOptions = async () => {
      try {
        const [fetchedCategories, fetchedSources] = await Promise.all([
          newsService.getCategories(),
          newsService.getSources()
        ]);
        setCategories(fetchedCategories);
        setSources(fetchedSources);
      } catch (err) {
        console.error("Failed to load filter options", err);
      }
    };
    fetchFilterOptions();
  }, []);
  
  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilters(prev => ({ ...prev, query: e.target.value }));
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setFilters(prev => ({ ...prev, category: e.target.value === 'ALL' ? undefined : e.target.value }));
  };

  const handleSourceChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setFilters(prev => ({ ...prev, source: e.target.value === 'ALL' ? undefined : e.target.value }));
  };

  const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilters(prev => ({ ...prev, date: e.target.value || undefined }));
  };

  return (
    <div className="glass-card p-4 rounded-2xl border border-gray-200 dark:border-slate-700 mb-8 sticky top-20 z-40 bg-white/80 dark:bg-slate-900/80 backdrop-blur-md">
      <div className="flex flex-col md:flex-row md:items-center space-y-4 md:space-y-0 md:space-x-4">
        
        {/* Search */}
        <div className="flex-grow relative">
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <Search className="h-5 w-5 text-gray-400 dark:text-gray-500" />
          </div>
          <input
            type="text"
            className="block w-full pl-10 pr-3 py-2 border border-gray-300 dark:border-slate-600 rounded-xl leading-5 bg-white dark:bg-slate-800 text-gray-900 dark:text-gray-100 placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm transition-colors"
            placeholder="Search headlines, topics..."
            value={filters.query || ''}
            onChange={handleSearchChange}
          />
        </div>

        {/* Filters Group */}
        <div className="flex items-center space-x-4">
          <div className="hidden md:flex items-center text-sm font-medium text-gray-700 dark:text-gray-300 bg-gray-50 dark:bg-slate-800 px-3 py-2 rounded-lg border border-gray-200 dark:border-slate-600">
            <Filter className="w-4 h-4 mr-2" />
            Filters
          </div>
          
          <select 
            className="block w-full pl-3 pr-10 py-2 text-base border-gray-300 dark:border-slate-600 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm rounded-xl border bg-white dark:bg-slate-800 text-gray-900 dark:text-gray-100"
            value={filters.category || 'ALL'}
            onChange={handleCategoryChange}
          >
            <option value="ALL">All Categories</option>
            {categories.map(cat => (
              <option key={cat} value={cat}>{cat}</option>
            ))}
          </select>

          <select 
            className="block w-full pl-3 pr-10 py-2 text-base border-gray-300 dark:border-slate-600 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm rounded-xl border bg-white dark:bg-slate-800 text-gray-900 dark:text-gray-100"
            value={filters.source || 'ALL'}
            onChange={handleSourceChange}
          >
            <option value="ALL">All Sources</option>
            {sources.map(src => (
              <option key={src} value={src}>{src}</option>
            ))}
          </select>
        </div>

      </div>
    </div>
  );
}
