import React from 'react';
import { Grid } from '@mui/material';
import MainCard from '../../../components/MainCard';
import RegionSearch from '../../../components/RegionSearch';

const RegionDetails = () => {
  return (
    <Grid>
      <MainCard>
        <RegionSearch/>
      </MainCard>
    </Grid>
  );
};

export default RegionDetails;