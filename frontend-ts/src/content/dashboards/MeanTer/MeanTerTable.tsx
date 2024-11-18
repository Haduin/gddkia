import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Box, Button, CircularProgress, Divider, Grid, Paper } from '@mui/material';
import React, { useState } from 'react';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { fetchJobs } from './actions';
import DateSelector from '../../../components/DateSelector';
import BranchSearch from '../../../components/BranchSearch';

interface JobData {
  id: number;
  sst: string;
  description: string;
  unit: string;
  groupType: string;
  subType: string;
  costEstimate: number;
}

const columns = [
  { field: 'sst', headerName: 'SST', width: 120 },
  { field: 'description', headerName: 'Opis', width: 700 },
  { field: 'unit', headerName: 'Jednostka', width: 100 },
  { field: 'groupType', headerName: 'Grupa', width: 250 },
  { field: 'subType', headerName: 'PodTyp', width: 200 },
  {
    headerName: 'Średnia cena',
    field: 'costEstimate',
    type: 'number',
    width: 120
  }
];

function Table() {
  const [rows, setRows] = useState<JobData[]>([]);
  const [loading, setLoading] = useState(false);

  const [selectedStartDate, setSelectedStartDate] = useState<Date | null>(null);
  const [formattedStartDate, setFormattedStartDate] = useState<string | null>(null);
  const [selectedEndDate, setSelectedEndDate] = useState<Date | null>(null);
  const [formattedEndDate, setFormattedEndDate] = useState<string | null>(null);

  const [selectedBranch, setSelectedBranch] = useState<string>('');
  const [selectedRegion, setSelectedRegion] = useState<string[]>([]);
  const [selectedSection, setSelectedSection] = useState<string[]>([]);

  const handleFetchData = () => {
    setLoading(true);
    fetchJobs(formattedStartDate, formattedEndDate, selectedBranch, selectedRegion, selectedSection)
      .then(response => {
        const timeout = setTimeout(() => {
          setRows(response.data.map((obj: any, index: number) => ({
            id: index,
            sst: obj.sst,
            description: obj.description,
            unit: obj.unit,
            groupType: obj.groupType,
            subType: obj.subType,
            costEstimate: obj.costEstimate
          })));
          setLoading(false);
        }, 1000);
        return () => clearTimeout(timeout);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
      });
  };

  return (
    <Grid>
      <Grid container style={{
        marginBottom: '20px',
        marginTop: '20px'
      }}>
        <Grid container style={{ margin: '5px' }}>
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
          <Grid item xs={12} md={4} sx={{ p: 1 }}>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DateSelector
                selectedDate={selectedStartDate}
                setSelectedDate={setSelectedStartDate}
                setFormattedDate={setFormattedStartDate}
                label="Wybierz date początkową"
              />
            </LocalizationProvider>
          </Grid>
          <Grid item xs={12} md={4} sx={{ p: 1 }}>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DateSelector
                selectedDate={selectedEndDate}
                setSelectedDate={setSelectedEndDate}
                setFormattedDate={setFormattedEndDate}
                label="Wybierz datę końcową"
              />
            </LocalizationProvider>
          </Grid>
          <Grid item xs={12} md={4} sx={{ p: 1 }}>
            <Button
              variant="contained"
              fullWidth
              onClick={handleFetchData}
            >
              Pobierz dane
            </Button>
          </Grid>
        </Grid>
      </Grid>
      <Divider />
      {loading ? (
        <Grid sx={{ display: 'flex', justifyContent: 'center', height: '500px' }}>
          <CircularProgress size={400} />
        </Grid>
      ) : (
        <>
          {rows.length > 0 && (
            <Box sx={{ p: 2 }}>
              <Paper>
                <DataGrid
                  rows={rows}
                  columns={columns}
                  initialState={{
                    pagination: {
                      pageSize: 10
                    }
                  }}
                  components={{
                    Toolbar: GridToolbar
                  }}
                  componentsProps={{
                    toolbar: {
                      showQuickFilter: true
                    }
                  }}
                  rowsPerPageOptions={[10, 20, 50]}
                />
              </Paper>
            </Box>
          )}
        </>
      )}
    </Grid>
  );
}

export default function MeanTerTable() {
  return (
    <Box justifyContent="center" alignItems="center">
      <Grid>
        <Table />
      </Grid>
    </Box>
  );
}
