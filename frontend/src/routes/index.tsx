import { createBrowserRouter } from 'react-router-dom';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Register from '../pages/Register';
import NewsFeed from '../pages/NewsFeed';
import Layout from '../components/layout/Layout';
import AdminRoute from '../components/layout/AdminRoute';
import AdminDashboard from '../pages/AdminDashboard';
import AdminUsers from '../pages/AdminUsers';
import AdminArticles from '../pages/AdminArticles';
import ErrorPage from '../pages/ErrorPage';
import SavedArticles from '../pages/SavedArticles';

export const router = createBrowserRouter([
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/register',
    element: <Register />,
  },
  {
    element: <Layout />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: '/',
        element: <Home />,
      },
      {
        path: '/news',
        element: <NewsFeed />,
      },
      {
        path: '/saved',
        element: <SavedArticles />,
      },
      {
        path: '/admin',
        element: <AdminRoute />,
        children: [
          { path: '', element: <AdminDashboard /> },
          { path: 'users', element: <AdminUsers /> },
          { path: 'articles', element: <AdminArticles /> },
        ]
      }
    ]
  }
]);
