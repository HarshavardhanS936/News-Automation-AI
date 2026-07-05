import React from 'react';

export default function SkeletonLoader() {
  return (
    <div className="bg-white dark:bg-slate-800 rounded-2xl p-4 w-full shadow-sm border border-gray-100 dark:border-slate-700 animate-pulse h-full flex flex-col">
      <div className="h-48 bg-gray-200 dark:bg-slate-700 rounded-xl mb-4"></div>
      <div className="flex-grow space-y-4 p-2">
        <div className="h-4 bg-gray-200 dark:bg-slate-700 rounded w-1/4"></div>
        <div className="space-y-2">
          <div className="h-6 bg-gray-200 dark:bg-slate-700 rounded w-full"></div>
          <div className="h-6 bg-gray-200 dark:bg-slate-700 rounded w-5/6"></div>
        </div>
        <div className="h-20 bg-gray-200 dark:bg-slate-700 rounded w-full mt-4"></div>
      </div>
      <div className="flex justify-between mt-4 p-2 pt-0">
        <div className="h-8 bg-gray-200 dark:bg-slate-700 rounded w-1/4"></div>
        <div className="h-8 bg-gray-200 dark:bg-slate-700 rounded w-1/4"></div>
      </div>
    </div>
  );
}
