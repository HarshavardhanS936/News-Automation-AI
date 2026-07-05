import React from 'react';
import { DivideIcon as LucideIcon } from 'lucide-react';

interface StatCardProps {
  title: string;
  value: string | number;
  icon: React.ElementType;
  trend?: {
    value: number;
    isPositive: boolean;
  };
}

export default function StatCard({ title, value, icon: Icon, trend }: StatCardProps) {
  return (
    <div className="glass-card rounded-2xl p-6 flex items-center shadow-sm hover:shadow-md transition-shadow animate-fade-in">
      <div className="p-3 bg-blue-50 dark:bg-blue-900/30 text-blue-600 dark:text-blue-400 rounded-xl">
        <Icon className="w-6 h-6" />
      </div>
      <div className="ml-4 flex-grow">
        <p className="text-sm font-medium text-gray-500 dark:text-gray-400">{title}</p>
        <h3 className="text-2xl font-bold text-gray-900 dark:text-white">{value}</h3>
      </div>
      {trend && (
        <div className={`flex items-center text-sm font-medium ${trend.isPositive ? 'text-green-600 dark:text-green-400' : 'text-red-600 dark:text-red-400'}`}>
          {trend.isPositive ? '↑' : '↓'}
          <span className="ml-1">{Math.abs(trend.value)}%</span>
        </div>
      )}
    </div>
  );
}
