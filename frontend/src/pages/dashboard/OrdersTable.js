import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
// material-ui
import { Box, Grid, TableContainer, TextField } from '@mui/material';

// third-party
// project import
import { useEffect, useState } from 'react';
import config from '../../config';

const columns = [
  { field: 'SST', headerName: 'SST', width: 80 },
  { field: 'description', headerName: 'Opis', width: 900 },
  { field: 'unit', headerName: 'Jednostka', width: 70 },
  {
    headerName: 'Kosztorys',
    field: 'costEstimate',
    type: 'number',
    width: 90
  },
  {
    headerName: 'Ilość',
    field: 'quantity',
    description: 'This column has a value getter and is not sortable.',
    width: 50
  },
  {
    headerName: 'Wycena',
    field: 'all',
    description: 'This column has a value getter and is not sortable.',
    width: 100
  }

];

function Table() {
  const [rows, setRows] = useState([]);
  const [filterText, setFilterText] = useState('');
  const [filteredRows, setFilteredRows] = useState(rows);
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
    axios.get(config.backend + '/jobs')
      .then(response => {
        setRows(response.data.map((obj, index) =>
          (
            {
              'id': index,
              'SST': obj.SST,
              'description': obj.description,
              'unit': obj.unit,
              'costEstimate': obj.costEstimate,
              'quantity': obj.quantity,
              'all': Math.round(obj.costEstimate * obj.quantity)
            }
          )
        ));
      });
  }, []);

  useEffect(() => {
    setFilteredRows(rows);
  }, [rows]);


  return (
    <Grid>
      <Grid style={{ display: 'flex', justifyContent: 'center' }}>
        <TextField
          label="Wyszukaj"
          variant="outlined"
          value={filterText}
          onChange={handleFilterChange}
          style={{ marginBottom: '20px', marginTop: '20px' }}
        />
      </Grid>
      <Grid style={{ display: 'flex', justifyContent: 'space-around', marginBottom: '2px' }}>
        {/*<FormControl fullWidth>*/}
        {/*  <InputLabel>Region</InputLabel>*/}
        {/*  <Select label="Nazwa regionu">*/}
        {/*    <MenuItem value={0}>Brak</MenuItem>*/}
        {/*    {region?.map(eln => (*/}
        {/*      <MenuItem key={eln.id} value={eln.id}>{eln.regionName}</MenuItem>*/}
        {/*    ))}*/}

        {/*  </Select>*/}
        {/*</FormControl>*/}
      </Grid>
      <Grid style={{ width: '100%' }}>
        <DataGrid
          rows={filteredRows}
          columns={columns}
        />
      </Grid>
    </Grid>
  );
}

Table.propTypes = {};


// ==============================|| ORDER TABLE ||============================== //

export default function OrderTable() {

  return (
    <Box>

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
    </Box>
  );
}
