import { lazy } from 'react';

// project import
import Loadable from 'components/Loadable';
import MainLayout from 'layout/MainLayout';

// render - dashboard
const MeanTerDashboard = Loadable(lazy(() => import('pages/dashboard/MeanTerDashboard')));
const Ter = Loadable(lazy(() => import('pages/dashboard/TerDashboard')));


// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
  path: '/',
  element: <MainLayout />,
  children: [
    {
      path: '/ter',
      element: <Ter />
    },
    {
      path: '/ter/srednie',
      element: <MeanTerDashboard />
    }
  ]
};

export default MainRoutes;
