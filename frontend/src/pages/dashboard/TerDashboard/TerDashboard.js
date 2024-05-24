import React, { useState } from 'react';
import { Button, CircularProgress, Grid, TextField } from '@mui/material';
import { Controller, useForm } from 'react-hook-form';
import { sendNewTer } from './actions';
import BranchSearch from '../../../components/BranchSearch';


const TerDashboard = () => {
  const [loading, setLoading] = useState(false);
  const [selectedBranch, setSelectedBranch] = useState('');
  const [selectedRegion, setSelectedRegion] = useState('');
  const [selectedSection, setSelectedSection] = useState('');
  const { control, register, formState: { errors }, handleSubmit, reset } = useForm({
    defaultValues: {
      companyName: '',
      contractNumber: '',
      // startDate: '',
      // endDate: '',
      file: ''
    }
  });

  const onSubmit = (data) => {
    sendNewTer(data.file[0], data.companyName, data.contractNumber, selectedBranch, selectedRegion, selectedSection)
      .then(response => {
        setLoading(true);
        const timeout = setTimeout(() => {
          if (response.status === 200) {
            alert('Poprawnie dodano formularz');
          } else {
            alert('Błąd formularza, spróbuj ponownie');
          }
          setLoading(false);
          reset();
          setSelectedBranch('');
        }, 1000);
        return () => clearTimeout(timeout);

      });
  };

  return (
    loading ?
      <Grid sx={{ display: 'flex', justifyContent: 'center' }}>
        <CircularProgress size={100} />
      </Grid>
      :
      <Grid>
        <form onSubmit={handleSubmit(onSubmit)}>
          <h2>Formularz dodawania nowego TERA</h2>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <Controller
                name="companyName"
                control={control}
                rules={{ required: 'Nazwa firmy jest wymagana' }}
                render={({ field, fieldState }) =>
                  <TextField
                    fullWidth
                    {...field}
                    placeholder="Nazwa Firmy"
                    error={!!fieldState.error}
                    helperText={fieldState.error ? fieldState.error.message : null}
                  />
                }
              />
            </Grid>

            <Grid item xs={12} sm={6}>
              <Controller
                name="contractNumber"
                control={control}
                rules={{ required: 'Nr umowy jest wymagany' }}
                render={({ field, fieldState }) =>
                  <TextField
                    fullWidth
                    {...field}
                    placeholder="Nr umowy"
                    error={!!fieldState.error}
                    helperText={fieldState.error ? fieldState.error.message : null}
                  />
                }
              />
            </Grid>

            <Grid item xs={12} sm={6}>
              <BranchSearch
                selectedBranch={selectedBranch}
                selectedRegion={selectedRegion}
                selectedSection={selectedSection}
                setSelectedBranch={setSelectedBranch}
                setSelectedRegion={setSelectedRegion}
                setSelectedSection={setSelectedSection} />
            </Grid>
            <Grid item xs={12} sm={6}>
              <input {...register('file', { required: 'Wymagany plik w formacie .xlsx lub .xls' })}
                     onChange={handleSubmit}
                     type="file" />
              {errors.file && <p style={{ color: 'red' }}>{errors.file.message}</p>}
            </Grid>
            <Grid item sm={12}>
              <Button variant="contained" type="submit">
                Zapisz
              </Button>
            </Grid>
          </Grid>
        </form>
      </Grid>


  );
};

export default TerDashboard;