
// material-ui
import {
  Grid
} from '@mui/material';

// project import
import OrdersTable from './OrdersTable';
import MainCard from 'components/MainCard';

// sales report status


// ==============================|| DASHBOARD - DEFAULT ||============================== //

const MeanTerDashboard = () => {

  return (
    <Grid container
          justifyContent="center"
          alignItems="center">
        <MainCard content={false}>
          <OrdersTable />
        </MainCard>

    </Grid>
  );
};

export default MeanTerDashboard;
