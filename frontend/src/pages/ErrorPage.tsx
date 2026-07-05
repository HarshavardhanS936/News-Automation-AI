import React from 'react';
import { AlertOctagon } from 'lucide-react';
import { useNavigate, useRouteError } from 'react-router-dom';

export default function ErrorPage() {
  const navigate = useNavigate();
  const error: any = useRouteError();

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-slate-900 flex flex-col items-center justify-center px-4 animate-fade-in">
      <div className="text-red-500 mb-6">
        <AlertOctagon className="w-20 h-20" />
      </div>
      <h1 className="text-4xl font-extrabold text-gray-900 dark:text-white mb-4 text-center">
        Oops! Something broke.
      </h1>
      <p className="text-lg text-gray-600 dark:text-gray-400 mb-8 text-center max-w-md">
        {error?.statusText || error?.message || "We encountered an unexpected error while loading this page."}
      </p>
      <div className="flex space-x-4">
        <button 
          onClick={() => navigate('/')} 
          className="bg-blue-600 text-white px-6 py-3 rounded-xl font-medium hover:bg-blue-700 transition-colors shadow-sm"
        >
          Go to Dashboard
        </button>
        <button 
          onClick={() => window.location.reload()} 
          className="bg-white dark:bg-slate-800 text-gray-900 dark:text-white border border-gray-200 dark:border-slate-700 px-6 py-3 rounded-xl font-medium hover:bg-gray-50 dark:hover:bg-slate-700 transition-colors shadow-sm"
        >
          Try Again
        </button>
      </div>
    </div>
  );
}
