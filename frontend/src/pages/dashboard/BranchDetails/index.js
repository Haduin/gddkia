import React, { useState } from 'react';
import { Grid } from '@mui/material';
import MainCard from '../../../components/MainCard';
import BranchSearch from '../../../components/BranchSearch';

const BranchDetails = () => {
  const [selectedBranch, setSelectedBranch] = useState([]);
  const [selectedRegion, setSelectedRegion] = useState([]);
  const [selectedSection, setSelectedSection] = useState([]);
  
  return (
    <Grid>
      <MainCard>
        <h2>Przykładowy element </h2>
        <BranchSearch
          selectedBranch={selectedBranch}
          selectedRegion={selectedRegion}
          selectedSection={selectedSection}
          setSelectedBranch={setSelectedBranch}
          setSelectedRegion={setSelectedRegion}
          setSelectedSection={setSelectedSection} />
        <div>
          <h2>Wybrane wartości:</h2>
          <p>Branch: {selectedBranch}</p>
          <p>Region: {selectedRegion}</p>
          <p>Section: {selectedSection}</p>
        </div>

      </MainCard>
    </Grid>
  );
};

export default BranchDetails;