// project import
import React from 'react';
import ThemeCustomization from 'themes';
import ScrollTop from 'components/ScrollTop';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import ThemeRoutes from './routes';

import 'dayjs/locale/pl'

const App = () => {
  return (
    <ThemeCustomization>
      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="pl">
        <ScrollTop>
          <ThemeRoutes />
        </ScrollTop>
      </LocalizationProvider>
    </ThemeCustomization>
  );
};

export default App;
