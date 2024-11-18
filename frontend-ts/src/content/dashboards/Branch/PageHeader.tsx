import { Typography, Grid } from '@mui/material';

function PageHeader() {
  return (
    <Grid container alignItems="center">
      <Grid item>
        <Typography variant="h3" component="h3" gutterBottom>
          Branch Dashboard
        </Typography>
        <Typography variant="subtitle2">
          Monitor and manage branch details
        </Typography>
      </Grid>
    </Grid>
  );
}

export default PageHeader;
