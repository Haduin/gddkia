// material-ui
import { Button, CardMedia, Link, Stack, Typography } from '@mui/material';

// project import
import MainCard from 'components/MainCard';

// assets
import AnimateButton from 'components/@extended/AnimateButton';

// ==============================|| DRAWER CONTENT - NAVIGATION CARD ||============================== //

const NavCard = () => (
  <MainCard sx={{ bgcolor: 'grey.50', m: 3 }}>
    <Stack alignItems="center" spacing={2.5}>
      <CardMedia component="img" sx={{ width: 112 }} />
      <Stack alignItems="center">
        <Typography variant="h5">Some</Typography>
        <Typography variant="h6" color="secondary">
          Checkout pro features
        </Typography>
      </Stack>
      <AnimateButton>
        <Button component={Link} target="_blank" variant="contained" color="success" size="small">
          Something
        </Button>
      </AnimateButton>
    </Stack>
  </MainCard>
);

export default NavCard;
