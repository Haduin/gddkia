// project import
import Routes from 'routes';
import ThemeCustomization from 'themes';
import ScrollTop from 'components/ScrollTop';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
// ==============================|| APP - THEME, ROUTER, LOCAL  ||============================== //

const App = () => (
  <ThemeCustomization>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
        <ScrollTop>
          <Routes />
        </ScrollTop>
    </LocalizationProvider>
  </ThemeCustomization>
);

export default App;
