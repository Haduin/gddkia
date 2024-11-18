import React, { useEffect } from 'react';
import { DatePicker } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import { Grid, TextField } from '@mui/material';

interface DateSelectorProps {
  selectedDate: Date | null;
  setSelectedDate: (date: Date | null) => void;
  label: string;
  setFormattedDate: (date: string | null) => void;
}

const DateSelector: React.FC<DateSelectorProps> = ({ 
  selectedDate, 
  setSelectedDate, 
  label, 
  setFormattedDate 
}) => {

  useEffect(() => {
    setFormattedDate(selectedDate ? format(new Date(selectedDate), 'yyyy/MM/dd') : '');
  }, [selectedDate]);

  return (
    <Grid item>
      <DatePicker
        label={label}
        value={selectedDate}
        onChange={(date: Date | null) => setSelectedDate(date)}
        renderInput={(params) => (
          <TextField
            {...params}
            variant="outlined"
            sx={{display: 'grid'}}
            fullWidth
          />
        )}
      />
    </Grid>

  );
};

export default DateSelector;
