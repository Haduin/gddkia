import { Typography, Grid } from '@mui/material';

function PageHeader() {
  return (
    <Grid container alignItems="center">
      <Grid item>
        <Typography variant="h3" component="h3" gutterBottom>
          TER Dashboard
        </Typography>
        <Typography variant="subtitle2">
          Formularz dodawania TER
        </Typography>
      </Grid>
    </Grid>
  );
}

export default PageHeader;
