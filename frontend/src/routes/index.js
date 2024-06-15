import { Navigate, Route, Routes } from 'react-router-dom';

// project import
import MainLayout from '../layout/MainLayout';
import TerDashboard from '../pages/dashboard/TerDashboard';
import React from 'react';
import MeanTerDashboard from '../pages/dashboard/MeanTerDashboard';
import BranchDetails from '../pages/dashboard/BranchDetails';
import SamplePage from '../pages/extra-pages/SamplePage';
import ErrorPage from './ErrorPage';

// ==============================|| ROUTING RENDER ||============================== //

export default function ThemeRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/app" replace />} />
      <Route path="/app" element={<MainLayout />} errorElement={<ErrorPage />}>
        <Route path="/app/ter" element={<TerDashboard />} />
        <Route path="/app/ter/srednie" element={<MeanTerDashboard />} />
        <Route path="/app/branch" element={<BranchDetails />} />
        <Route path="/app/sample" element={<SamplePage />} />
      </Route>
      <Route path="*" element={<ErrorPage />} />
    </Routes>
  );
}
