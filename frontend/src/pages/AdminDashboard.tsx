import React, { useEffect, useState } from 'react';
import { adminService } from '../services/adminService';
import { Activity, Database, Users, FileText, RefreshCw } from 'lucide-react';
import StatCard from '../components/dashboard/StatCard';
import { Link } from 'react-router-dom';

export default function AdminDashboard() {
  const [status, setStatus] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchStatus();
  }, []);

  const fetchStatus = async () => {
    try {
      const data = await adminService.getSystemStatus();
      setStatus(data);
    } catch (err: any) {
      setError(err.response?.status === 403 ? 'Access Denied. Admin privileges required.' : 'Failed to fetch status.');
    } finally {
      setLoading(false);
    }
  };

  const handleRefreshNews = async () => {
    setRefreshing(true);
    try {
      await adminService.refreshNews();
      alert('News refresh triggered successfully.');
      fetchStatus();
    } catch (err) {
      alert('Failed to trigger news refresh.');
    } finally {
      setRefreshing(false);
    }
  };

  if (loading) return <div className="p-8 text-center">Loading Admin Panel...</div>;
  if (error) return <div className="p-8 text-center text-red-600 font-bold">{error}</div>;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8 flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-900 dark:text-white">Admin Control Panel</h1>
          <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">System status and management overview.</p>
        </div>
        <div className="flex space-x-4">
          <Link to="/admin/users" className="bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-gray-700 dark:text-gray-200 px-4 py-2 rounded-lg font-medium hover:bg-gray-50 dark:hover:bg-gray-700">
            Manage Users
          </Link>
          <Link to="/admin/articles" className="bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-700 text-gray-700 dark:text-gray-200 px-4 py-2 rounded-lg font-medium hover:bg-gray-50 dark:hover:bg-gray-700">
            Manage Articles
          </Link>
          <button 
            onClick={handleRefreshNews}
            disabled={refreshing}
            className="bg-blue-600 text-white px-4 py-2 rounded-lg font-medium flex items-center hover:bg-blue-700 disabled:opacity-50"
          >
            <RefreshCw className={`w-4 h-4 mr-2 ${refreshing ? 'animate-spin' : ''}`} />
            {refreshing ? 'Refreshing...' : 'Trigger News Aggregator'}
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard title="API Status" value={status.apiStatus} icon={Activity} />
        <StatCard title="Database Status" value={status.dbStatus} icon={Database} />
        <StatCard title="Total Users" value={status.totalUsers} icon={Users} />
        <StatCard title="Total Articles" value={status.totalArticles} icon={FileText} />
      </div>
    </div>
  );
}
