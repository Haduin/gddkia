import React, { useEffect, useState } from 'react';
import { FormControl, Grid, InputLabel, MenuItem, Select } from '@mui/material';
import { fetchBranch } from '../../pages/dashboard/BranchDetails/action';

const BranchSearch = ({
                        selectedBranch,
                        selectedRegion,
                        selectedSection,
                        setSelectedBranch,
                        setSelectedRegion,
                        setSelectedSection
                      }) => {
  const [data, setData] = useState([]);
  const [filteredRegions, setFilteredRegions] = useState([]);
  const [filteredSections, setFilteredSections] = useState([]);

  const branches = [...new Set(data.map(item => item.branch))];

  useEffect(() => {
    fetchBranch().then(res => setData(res.data));
  }, []);

  useEffect(() => {
    if (selectedBranch) {
      const regions = data
        .filter(item => item.branch === selectedBranch)
        .map(item => item.region);
      setFilteredRegions([...new Set(regions)]);
      setSelectedRegion([]);
      setSelectedSection([]);
    } else {
      setFilteredRegions([]);
      setSelectedRegion([]);
      setSelectedSection([]);
    }
  }, [selectedBranch]);

  useEffect(() => {
    if (selectedRegion.length > 0) {
      const sections = data
        .filter(item => item.branch === selectedBranch && selectedRegion.includes(item.region))
        .map(item => item.section);
      setFilteredSections([...new Set(sections)]);
      setSelectedSection([]);
    } else {
      setFilteredSections([]);
      setSelectedSection([]);
    }
  }, [selectedRegion]);


  return data.length ? (
    <Grid container spacing={0.5}>
      <Grid item xs={12} md={4}>
        <FormControl fullWidth ariant="filled" sx={{ p: 1 }}>
          <InputLabel id="branch-select-label">Oddział</InputLabel>
          <Select
            labelId="branch-select-label"
            value={selectedBranch}
            onChange={(e) => {
              setSelectedBranch(e.target.value);
              setSelectedRegion([]);
              setSelectedSection([]);
            }}
          >

            {branches.map((branch, index) => (
              <MenuItem key={index} value={branch}>
                {branch}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

      </Grid>

      <Grid item xs={12} md={4}>
        {selectedBranch.length > 0 && (
          <FormControl fullWidth ariant="filled" sx={{ p: 1 }}>
            <InputLabel id="region-select-label">Region</InputLabel>
            <Select
              multiple
              labelId="region-select-label"
              value={selectedRegion}
              onChange={(e) => {
                setSelectedRegion(e.target.value);
                setSelectedSection([]);
              }}
            >
              {filteredRegions.map((region, index) => (
                <MenuItem key={index} value={region}>
                  {region}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        )}
      </Grid>

      <Grid item xs={12} md={4}>
        {selectedRegion.length > 0 && (
          <FormControl fullWidth ariant="filled" sx={{ p: 1 }}>
            <InputLabel id="section-select-label">Sektor</InputLabel>
            <Select
              multiple
              labelId="section-select-label"
              value={selectedSection}
              onChange={(e) => setSelectedSection(e.target.value)}
            >
              {filteredSections.map((section, index) => (
                <MenuItem key={index} value={section}>
                  {section}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        )}
      </Grid>

    </Grid>

  ) : <></>;
};

export default BranchSearch;
