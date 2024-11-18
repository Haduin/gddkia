import { Helmet } from 'react-helmet-async';
import PageHeader from './PageHeader';
import PageTitleWrapper from 'src/components/PageTitleWrapper';
import { Container, Grid } from '@mui/material';
import Footer from 'src/components/Footer';
import BranchDetails from './BranchDetails';

function DashboardBranch() {
  return (
    <>
      <Helmet>
        <title>Branch Dashboard</title>
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
            <BranchDetails />
          </Grid>
        </Grid>
      </Container>
      <Footer />
    </>
  );
}

export default DashboardBranch;
