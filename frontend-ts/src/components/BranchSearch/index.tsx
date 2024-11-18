import { FC, useEffect, useState } from 'react';
import { FormControl, Grid, InputLabel, MenuItem, Select } from '@mui/material';
import { fetchBranch } from '../../content/dashboards/Branch/actions';

interface BranchSearchProps {
  selectedBranch: string;
  selectedRegion: string[];
  selectedSection: string[];
  setSelectedBranch: (branch: string) => void;
  setSelectedRegion: (region: string[]) => void;
  setSelectedSection: (section: string[]) => void;
}

interface BranchData {
  branch: string;
  region: string;
  section: string;
}

const BranchSearch: FC<BranchSearchProps> = ({
  selectedBranch,
  selectedRegion,
  selectedSection,
  setSelectedBranch,
  setSelectedRegion,
  setSelectedSection
}) => {
  const [data, setData] = useState<BranchData[]>([]);
  const [filteredRegions, setFilteredRegions] = useState<string[]>([]);
  const [filteredSections, setFilteredSections] = useState<string[]>([]);

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
  }, [selectedBranch, data, setSelectedRegion, setSelectedSection]);

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
  }, [selectedRegion, data, selectedBranch, setSelectedSection]);

  return data.length ? (
    <Grid container spacing={0.5}>
      <Grid item xs={12} md={4}>
        <FormControl fullWidth variant="filled" sx={{ p: 1 }}>
          <InputLabel id="branch-select-label">Oddział</InputLabel>
          <Select
            labelId="branch-select-label"
            value={selectedBranch || ''}
            onChange={(e) => {
              setSelectedBranch(e.target.value as string);
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
          <FormControl fullWidth variant="filled" sx={{ p: 1 }}>
            <InputLabel id="region-select-label">Region</InputLabel>
            <Select
              multiple
              labelId="region-select-label"
              value={selectedRegion}
              onChange={(e) => {
                setSelectedRegion(e.target.value as string[]);
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
          <FormControl fullWidth variant="filled" sx={{ p: 1 }}>
            <InputLabel id="section-select-label">Sektor</InputLabel>
            <Select
              multiple
              labelId="section-select-label"
              value={selectedSection}
              onChange={(e) => setSelectedSection(e.target.value as string[])}
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
