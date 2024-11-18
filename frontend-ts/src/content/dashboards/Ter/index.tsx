import { Helmet } from 'react-helmet-async';
import PageHeader from './PageHeader';
import PageTitleWrapper from 'src/components/PageTitleWrapper';
import { Container, Grid } from '@mui/material';
import Footer from 'src/components/Footer';
import TerDashboard from './TerDashboard';

const DashboardTer = () => {
  return (
    <>
      <Helmet>
        <title>Ter Dashboard</title>
      </Helmet>
      <PageTitleWrapper>
        <PageHeader />
      </PageTitleWrapper>
      <Container maxWidth="lg">
        <Grid
          container
          direction="row"
          justifyContent="center"
          alignItems="stretch"
          spacing={4}
        >
          <Grid item xs={12}>
            <TerDashboard />
          </Grid>
        </Grid>
      </Container>
      <Footer />
    </>
  );
}

export default DashboardTer;
