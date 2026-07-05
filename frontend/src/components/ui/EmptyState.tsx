import React from 'react';
import { Ghost } from 'lucide-react';

interface EmptyStateProps {
  message: string;
  subMessage?: string;
}

export default function EmptyState({ message, subMessage }: EmptyStateProps) {
  return (
    <div className="flex flex-col items-center justify-center py-20 px-4 text-center animate-fade-in">
      <div className="w-24 h-24 bg-gray-100 dark:bg-slate-800 rounded-full flex items-center justify-center mb-6">
        <Ghost className="w-12 h-12 text-gray-400 dark:text-gray-500" />
      </div>
      <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-2">{message}</h3>
      {subMessage && (
        <p className="text-gray-500 dark:text-gray-400 max-w-sm">{subMessage}</p>
      )}
    </div>
  );
}
