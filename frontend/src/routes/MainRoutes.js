import { lazy } from 'react';

// project import
import Loadable from 'components/Loadable';
import MainLayout from 'layout/MainLayout';
import ErrorPage from './ErrorPage';

// render - dashboard
const MeanTerDashboard = Loadable(lazy(() => import('pages/dashboard/MeanTerDashboard')));
const RegionDashboard = Loadable(lazy(() => import('pages/dashboard/BranchDetails')));
const Ter = Loadable(lazy(() => import('pages/dashboard/TerDashboard')));
const SamplePage = Loadable(lazy(() => import('pages/extra-pages/SamplePage')));



// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
  path: '/app',
  element: <MainLayout />,
  errorElement: <ErrorPage/>,
  children: [
    {
      path: '/app/ter',
      element: <Ter />
    },
    {
      path: '/app/ter/srednie',
      element: <MeanTerDashboard />
    },
    {
      path: '/app/region',
      element: <RegionDashboard />
    },
    {
      path: '/app/sample',
      element: <SamplePage />
    }
  ],

};

export default MainRoutes;
