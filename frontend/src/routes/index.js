import { Route, Routes } from 'react-router-dom';

// project import
import { useAuthentication } from '../hooks/useAuthentication';
import MainLayout from '../layout/MainLayout';
import TerDashboard from '../pages/dashboard/TerDashboard';
import React from 'react';
import MeanTerDashboard from '../pages/dashboard/MeanTerDashboard';
import RegionDetails from '../pages/dashboard/RegionDetails';
import SamplePage from '../pages/extra-pages/SamplePage';
import Login from '../pages/authentication/Login';
import ErrorPage from './ErrorPage';

// ==============================|| ROUTING RENDER ||============================== //

export default function ThemeRoutes() {
  const { isAuthenticated } = useAuthentication();
  return (

    <Routes>
      {isAuthenticated ?
        <Route path="">
          <Route path="/app"
                 element={<MainLayout />}
                 errorElement={<ErrorPage />}
          >
            <Route path="/app/ter" element={<TerDashboard />} />
            <Route path="/app/ter/srednie" element={<MeanTerDashboard />} />
            <Route path="/app/region" element={<RegionDetails />} />
            <Route path="/app/sample" element={<SamplePage />} />
            <Route path="*" element={<ErrorPage />} />

          </Route>
          <Route path="*" element={<ErrorPage />} />
        </Route>

        :
        <Route path="*" element={<Login />} />
      }

    </Routes>
  );
}
