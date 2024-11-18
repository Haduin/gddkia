import { Suspense, lazy } from 'react';
import { Navigate } from 'react-router-dom';
import { RouteObject } from 'react-router';

import SidebarLayout from 'src/layouts/SidebarLayout';
import BaseLayout from 'src/layouts/BaseLayout';

import SuspenseLoader from 'src/components/SuspenseLoader';

const Loader = (Component) => (props) =>
  (
    <Suspense fallback={<SuspenseLoader />}>
      <Component {...props} />
    </Suspense>
  );

// Pages

const Signup = Loader(lazy(() => import('src/components/Auth/signup')));
const Login = Loader(lazy(() => import('src/components/Auth/login')));

// Dashboards
const Branch = Loader(lazy(() => import('src/content/dashboards/Branch')));
const MeanTer = Loader(lazy(() => import('src/content/dashboards/MeanTer/index')));
const Ter = Loader(lazy(() => import('src/content/dashboards/Ter/index')));

// Status
const Status404 = Loader(lazy(() => import('src/content/pages/Status/Status404')));

const routes: RouteObject[] = [
  {
    path: '',
    element: <BaseLayout />,
    children: [
      {
        path: '/',
        element: <Signup />
      },
      {
        path: 'signup',
        element: <Navigate to="/" replace />
      },
      {
        path: 'signup',
        element: <Signup />
      },
      {
        path: 'login',
        element: <Login />,
      },
      {
        path: '/overview',
        element: <Navigate to="/dashboards/branch" replace />
      },
      {
        path: 'status',
        children: [
          {
            path: '',
            element: <Navigate to="404" replace />
          },
          {
            path: '404',
            element: <Status404 />
          },
        ]
      },
      {
        path: '*',
        element: <Status404 />
      }
    ]
  },
  {
    path: 'dashboards',
    element: <SidebarLayout />,
    children: [
      {
        path: '',
        element: <Navigate to="branch" replace />
      },
      {
        path: 'branch',
        element: <Branch />
      },
      {
        path: 'ter',
        element: <Ter />
      },
      {
        path: 'mean-ter',
        element: <MeanTer />
      }
    ]
  }
];

export default routes;
