import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import Navbar from './Navbar';
import { Toaster } from 'react-hot-toast';

export default function Layout() {
  return (
    <div className="min-h-screen">
      <Navbar />
      <main className="pt-4">
        <Outlet />
      </main>
      <Toaster position="bottom-right" />
    </div>
  );
}
