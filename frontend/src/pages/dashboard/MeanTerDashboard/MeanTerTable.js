import { DataGrid } from '@mui/x-data-grid';
// material-ui
import { Box, Grid, TableContainer, TextField } from '@mui/material';

// third-party
// project import
import { useEffect, useState } from 'react';
import { fetchJobs } from './actions';

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
    fetchJobs()
      .then(response => {
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
      }).catch(err => {
        console.log(err)
    });
  }, []);

  useEffect(() => {
    setFilteredRows(rows);
  }, [rows]);


  return (
    <Grid style={{ width: '100%' }}>
      <Grid style={{ display: 'flex', justifyContent: 'center' }}>
        <TextField
          label="Wyszukaj"
          variant="outlined"
          value={filterText}
          onChange={handleFilterChange}
          style={{ marginBottom: '20px', marginTop: '20px' }}
        />
      </Grid>
      <Grid>
        <DataGrid
          rows={filteredRows}
          columns={columns}
          initialState={{
            pagination: { paginationModel: { pageSize: 10 } },
          }}
          pageSizeOptions={[5, 10, 25]}
        />
      </Grid>
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
