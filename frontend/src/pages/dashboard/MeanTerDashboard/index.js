// material-ui
import {
  Grid
} from '@mui/material';

// project import
import MainCard from 'components/MainCard';
import MeanTerTable from './MeanTerTable';

// sales report status


// ==============================|| DASHBOARD - DEFAULT ||============================== //

const MeanTerDashboard = () => {

  return (
    <Grid container justifyContent="center" alignItems="center">
      <MainCard content={false}>
        <MeanTerTable />
      </MainCard>
    </Grid>
  );
};

export default MeanTerDashboard;
