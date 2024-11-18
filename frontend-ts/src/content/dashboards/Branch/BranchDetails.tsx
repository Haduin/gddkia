import { FC, useState } from 'react';
import {
  Card,
  Box,
  Typography,
  Grid
} from '@mui/material';
import BranchSearch from '../../../components/BranchSearch';

const BranchDetails: FC = () => {
  const [selectedBranch, setSelectedBranch] = useState<string>('');
  const [selectedRegion, setSelectedRegion] = useState<string[]>([]);
  const [selectedSection, setSelectedSection] = useState<string[]>([]);

  return (
    <Card>
      <Box p={3}>
        <Typography variant="h4">Branch Details</Typography>
        <Grid container spacing={3} mt={1}>
          <Grid item xs={12}>
            <BranchSearch
              selectedBranch={selectedBranch}
              selectedRegion={selectedRegion}
              selectedSection={selectedSection}
              setSelectedBranch={setSelectedBranch}
              setSelectedRegion={setSelectedRegion}
              setSelectedSection={setSelectedSection}
            />
          </Grid>
          <Grid item xs={12}>
            <Typography variant="h5">Selected Values:</Typography>
            <Typography>Branch: {selectedBranch}</Typography>
            <Typography>Region: {selectedRegion.join(', ')}</Typography>
            <Typography>Section: {selectedSection.join(', ')}</Typography>
          </Grid>
        </Grid>
      </Box>
    </Card>
  );
};

export default BranchDetails;
