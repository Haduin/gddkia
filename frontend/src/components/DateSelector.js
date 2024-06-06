import React, { useEffect } from 'react';
import { DatePicker } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import { Grid } from '@mui/material';

const DateSelector = ({ selectedDate, setSelectedDate, label, setFormattedDate }) => {

  useEffect(() => {
    setFormattedDate(selectedDate ? format(new Date(selectedDate), 'dd/MM/yyyy') : '');
  }, [selectedDate]);

  return (
    <Grid item>
      <DatePicker
        sx={{display:'grid'}}
        autoWidth={true}
        label={label}
        value={selectedDate}
        format="DD/MM/YYYY"
        onChange={(date) => setSelectedDate(date)}
        isRequired={true}
        slotProps={{ textField: { variant: 'outlined' } }}
      />
    </Grid>

  );
};

export default DateSelector;