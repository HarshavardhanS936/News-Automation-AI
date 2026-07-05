import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';

interface AuthContextType {
  token: string | null;
  username: string | null;
  roles: string[];
  isAdmin: boolean;
  login: (token: string, username: string, roles: string[]) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(localStorage.getItem('token'));
  const [username, setUsername] = useState<string | null>(localStorage.getItem('username'));
  const [roles, setRoles] = useState<string[]>(() => {
    const storedRoles = localStorage.getItem('roles');
    return storedRoles ? JSON.parse(storedRoles) : [];
  });

  const login = (newToken: string, newUsername: string, newRoles: string[]) => {
    setToken(newToken);
    setUsername(newUsername);
    setRoles(newRoles);
    localStorage.setItem('token', newToken);
    localStorage.setItem('username', newUsername);
    localStorage.setItem('roles', JSON.stringify(newRoles));
  };

  const logout = () => {
    setToken(null);
    setUsername(null);
    setRoles([]);
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('roles');
  };

  const isAdmin = roles.includes('ROLE_ADMIN');

  return (
    <AuthContext.Provider value={{ token, username, roles, isAdmin, login, logout, isAuthenticated: !!token }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
