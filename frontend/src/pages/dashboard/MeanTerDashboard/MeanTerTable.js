import { DataGrid } from '@mui/x-data-grid';
import { Box, Button, CircularProgress, Divider, Grid, TableContainer, TextField } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { fetchJobs } from './actions';
import { useAuthentication } from '../../../hooks/useAuthentication';
import DateSelector from '../../../components/DateSelector';

const columns = [
  { field: 'sst', headerName: 'SST', width: 120 },
  { field: 'description', headerName: 'Opis', width: 700 },
  { field: 'unit', headerName: 'Jednostka', width: 100 },
  {
    headerName: 'Sredni kosztorys',
    field: 'costEstimate',
    type: 'number',
    width: 120
  }
];

function Table() {
  const { handleLogout } = useAuthentication();
  const [rows, setRows] = useState([]);
  const [filterText, setFilterText] = useState('');
  const [filteredRows, setFilteredRows] = useState(rows);

  const [selectedStartDate, setSelectedStartDate] = useState(null);
  const [formattedStartDate, setFormattedStartDate] = useState(null);
  const [selectedEndDate, setSelectedEndDate] = useState(null);
  const [formattedEndDate, setFormattedEndDate] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleFilterChange = (event) => {
    const value = event.target.value.toLowerCase();
    setFilterText(value);

    const filteredData = rows.filter(row => {
      return columns.some(column => {
        const field = column.field;
        const cellValue = String(row[field]).toLowerCase();
        return cellValue.includes(value);
      });
    });

    setFilteredRows(filteredData);
  };

  useEffect(() => {
    setFilteredRows(rows);
  }, [rows]);

  const handleFetchData = () => {
    setLoading(true);
    fetchJobs()
      .then(response => {
        const timeout = setTimeout(()=>{
          setRows(response.data.map((obj, index) =>
            (
              {
                'id': index,
                'sst': obj.sst,
                'description': obj.description,
                'unit': obj.unit,
                'costEstimate': obj.costEstimate
              }
            )
          ));
          setLoading(false)
        },1000)
        return () => clearTimeout(timeout)
      })
      .catch(err => {
        if (err.response.status === 403)
          handleLogout();
      });
  };

  return (
    <Grid style={{ width: '100%' }}>
      <Grid container style={{
        display: 'flex',
        flexWrap: 'nowrap',
        justifyContent: 'left',
        marginBottom: '20px',
        marginTop: '20px'
      }}>
        <Grid item xs={12} md={4} style={{ marginLeft: '5px' }}>
          <TextField
            label="Wyszukaj"
            variant="outlined"
            value={filterText}
            onChange={handleFilterChange}
          />
        </Grid>
        <Grid item xs={12} md={8} style={{ display: 'flex', justifyContent: 'end', marginRight: '5px' }}>
          <Grid>
            <DateSelector
              selectedDate={selectedStartDate}
              setSelectedDate={setSelectedStartDate}
              setFormattedDate={setFormattedStartDate}
              name="selectedDate"
              label="Wybierz date początkową"
            />
          </Grid>
          <Grid>
            <DateSelector
              selectedDate={selectedEndDate}
              setSelectedDate={setSelectedEndDate}
              setFormattedDate={setFormattedEndDate}
              name="selectedDate"
              label="Wybierz datę końcową"
            />
          </Grid>
          <Button variant="outlined"
                  onClick={handleFetchData}
          >
            Pobierz dane
          </Button>
        </Grid>
      </Grid>
      <Divider />
      {loading ?
        <Grid sx={{ display: 'flex', justifyContent: 'center', height: '500px' }}>
          <CircularProgress size={400} />
        </Grid>
        : (
          <Grid>
            <DataGrid
              rows={filteredRows}
              columns={columns}
              initialState={{
                pagination: { paginationModel: { pageSize: 10 } }
              }}
              pageSizeOptions={[5, 10, 25]}
            />
          </Grid>
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
