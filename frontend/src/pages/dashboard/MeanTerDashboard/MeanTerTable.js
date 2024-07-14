import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Box, Button, CircularProgress, Divider, Grid, Paper, TableContainer } from '@mui/material';
import React, { useState } from 'react';
import { fetchJobs } from './actions';
import DateSelector from '../../../components/DateSelector';
import BranchSearch from '../../../components/BranchSearch';

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
  const [rows, setRows] = useState([]);

  const [selectedStartDate, setSelectedStartDate] = useState(null);
  const [formattedStartDate, setFormattedStartDate] = useState(null);
  const [selectedEndDate, setSelectedEndDate] = useState(null);
  const [formattedEndDate, setFormattedEndDate] = useState(null);
  const [loading, setLoading] = useState(false);


  const [selectedBranch, setSelectedBranch] = useState([]);
  const [selectedRegion, setSelectedRegion] = useState([]);
  const [selectedSection, setSelectedSection] = useState([]);

  const handleFetchData = () => {
    setLoading(true);
    fetchJobs(formattedStartDate, formattedEndDate, selectedBranch, selectedRegion, selectedSection)
      .then(response => {
        const timeout = setTimeout(() => {
          setRows(response.data.map((obj, index) =>
            (
              {
                'id': index,
                'sst': obj.sst,
                'description': obj.description,
                'unit': obj.unit,
                'groupType': obj.groupType,
                'subType': obj.subType,
                'costEstimate': obj.costEstimate
              }
            )
          ));
          setLoading(false);
        }, 1000);
        return () => clearTimeout(timeout);
      })
      .catch(err => {
        console.log(err)
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
              setSelectedSection={setSelectedSection} />
          </Grid>
          <Grid item xs={12} md={4} sx={{ p: 1 }}>
            <DateSelector
              selectedDate={selectedStartDate}
              setSelectedDate={setSelectedStartDate}
              setFormattedDate={setFormattedStartDate}
              name="selectedDate"
              label="Wybierz date początkową"
            />
          </Grid>
          <Grid item xs={12} md={4} sx={{ p: 1 }}>
            <DateSelector
              selectedDate={selectedEndDate}
              setSelectedDate={setSelectedEndDate}
              setFormattedDate={setFormattedEndDate}
              name="selectedDate"
              label="Wybierz datę końcową"
            />
          </Grid>
          <Grid item xs={12} md={4} sx={{ p: 1 }}>
            <Button variant="contained"
                    fullWidth
                    onClick={handleFetchData}
            >
              Pobierz dane
            </Button>
          </Grid>
        </Grid>
      </Grid>
      <Divider />
      {loading ?
        <Grid sx={{ display: 'flex', justifyContent: 'center', height: '500px' }}>
          <CircularProgress size={400} />
        </Grid>
        : (
          <>
            {rows.length > 0 ? <Box sx={{ p: 2 }}>
              <Paper>
                <DataGrid
                  rows={rows}
                  columns={columns}
                  initialState={{
                    pagination: { paginationModel: { pageSize: 10 } }
                  }}
                  slotProps={{
                    toolbar: {
                      showQuickFilter: true
                    }
                  }}
                  slots={{ toolbar: GridToolbar }}
                  pageSizeOptions={[10, 20, 50]}
                />
              </Paper>
            </Box> : <></>}
          </>
        )}
    </Grid>
  );
}

Table.propTypes = {};


// ==============================|| ORDER TABLE ||============================== //

export default function MeanTerTable() {

  return (
    <Box justifyContent="center" alignItems="center">
      <Grid>
        <TableContainer
          sx={{
            width: '100%',
            overflowX: 'auto',
            position: 'relative',
            display: 'block',
            maxWidth: '100%',
            '& td, & th': { whiteSpace: 'nowrap' }
          }}
        >
          <Table />
        </TableContainer>
      </Grid>
    </Box>
  );
}
