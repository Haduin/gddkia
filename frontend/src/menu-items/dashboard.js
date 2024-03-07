// assets
import { DashboardOutlined } from '@ant-design/icons';

// icons
const icons = {
  DashboardOutlined
};

// ==============================|| MENU ITEMS - DASHBOARD ||============================== //

const dashboard = {
  id: 'group-dashboard',
  title: 'Pozycje asortymentu',
  type: 'group',
  children: [
    {
      id: 'dashboard1',
      title: 'Jednostkowe ceny prac',
      type: 'item',
      url: '/ter',
      icon: icons.DashboardOutlined,
      breadcrumbs: false
    },
    {
      id: 'dashboard',
      title: 'Średnie ceny prac',
      type: 'item',
      url: '/ter/srednie',
      icon: icons.DashboardOutlined,
      breadcrumbs: false
    }
  ]
};

export default dashboard;
