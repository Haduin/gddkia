import React from 'react';
import { Grid } from '@mui/material';
import MainCard from '../../../components/MainCard';
import TerDashboard from './TerDashboard';

const Index = () => {
  return (
    <Grid>
      <MainCard>
        <TerDashboard />
      </MainCard>
    </Grid>
  );
};

export default Index;
