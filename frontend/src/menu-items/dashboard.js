// assets
import { DashboardOutlined, PartitionOutlined, TableOutlined } from '@ant-design/icons';
// icons
const icons = {
  DashboardOutlined,
  PartitionOutlined,
  TableOutlined
};

// ==============================|| MENU ITEMS - DASHBOARD ||============================== //

const dashboard = {
  id: 'group-dashboard',
  title: 'Pozycje asortymentu',
  type: 'group',
  children: [
    {
      id: 'dashboard1',
      title: 'Dodawanie nowego TER',
      type: 'item',
      url: '/app/ter',
      icon: icons.DashboardOutlined,
      breadcrumbs: false
    },
    {
      id: 'dashboard2',
      title: 'Średnie ceny prac',
      type: 'item',
      url: '/app/ter/srednie',
      icon: icons.TableOutlined,
      breadcrumbs: false
    },
    {
      id: 'dashboard3',
      title: 'Oddziały',
      type: 'item',
      url: '/app/branch',
      icon: icons.PartitionOutlined,
      breadcrumbs: false
    }
  ]
};

export default dashboard;
