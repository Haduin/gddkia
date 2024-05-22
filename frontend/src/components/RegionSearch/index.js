import React from 'react';
import { FormControl, Grid, InputLabel, MenuItem, Select } from '@mui/material';

const RegionSearch = () => {
  const [age, setAge] = React.useState('');

  const handleChange = (event) => {
    setAge(event.target.value);
  };

  return (
    <Grid container spacing={2}>
      <Grid item>
        <FormControl variant="filled" sx={{ m: 1, minWidth: 350 }}>
          <InputLabel id="demo-simple-select-filled-label">Age</InputLabel>
          <Select
            labelId="demo-simple-select-filled-label"
            id="demo-simple-select-filled"
            value={age}
            onChange={handleChange}
          >
            <MenuItem value="">
              <em>None</em>
            </MenuItem>
            <MenuItem value={10}>Ten</MenuItem>
            <MenuItem value={20}>Twenty</MenuItem>
            <MenuItem value={30}>Thirty</MenuItem>
          </Select>
        </FormControl>
      </Grid>
    </Grid>

  );
};

export default RegionSearch;

//Grid item xs={12}>
//           <FormControl variant="filled" sx={{ m: 1, minWidth: 350 }}>
//             <InputLabel id="demo-simple-select-filled-label">Age</InputLabel>
//             <Select
//               labelId="demo-simple-select-filled-label"
//               id="demo-simple-select-filled"
//               value={age}
//               onChange={handleChange}
//             >
//               <MenuItem value="">
//                 <em>None</em>
//               </MenuItem>
//               <MenuItem value={10}>Ten</MenuItem>
//               <MenuItem value={20}>Twenty</MenuItem>
//               <MenuItem value={30}>Thirty</MenuItem>
//             </Select>
//           </FormControl>
//         </Grid>