import React, { useState } from 'react';
import { Button, CircularProgress, Grid, TextField } from '@mui/material';
import { Controller, useForm } from 'react-hook-form';
import { sendNewTer } from './actions';


// const Item = styled('div')(({ theme, border = false }) => ({
//   backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
//   ...theme.typography.body2,
//   padding: theme.spacing(1),
//   textAlign: 'center',
//   color: theme.palette.text.secondary,
//   borderStyle: border ? 'solid' : 'none'
// }));

const TerDashboard = () => {
  const [loading, setLoading] = useState(false);
  const { control, register, formState: { errors }, handleSubmit, reset } = useForm({
    defaultValues: {
      companyName: '',
      contractNumber: '',
      regionName: 'Siedlce',
      branchName: 'Warszawa',
      // startDate: '',
      // endDate: '',
      file: ''
    }
  });

  const onSubmit = (data) => {
    sendNewTer(data.file[0], data.companyName, data.contractNumber, data.regionName, data.branchName)
      .then(response => {
        setLoading(true);
        if (response.status === 200) {
          alert('Poprawnie dodano formularz');
        } else {
          alert('Błąd formularza, spróbuj ponownie');
        }
        setLoading(false);
        reset();
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
              <Controller
                name="regionName"
                control={control}
                rules={{ required: 'Region musi być wybrany' }}
                render={({ field, fieldState }) =>
                  <TextField
                    fullWidth
                    {...field}
                    placeholder="Region"
                    disabled={true}
                    error={!!fieldState.error}
                    helperText={fieldState.error ? fieldState.error.message : null}
                  />
                }
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <Controller
                name="branchName"
                control={control}
                rules={{ required: 'Oddział musi być wybrany' }}
                render={({ field, fieldState }) =>
                  <TextField
                    {...field}
                    fullWidth
                    placeholder="Oddział"
                    disabled={true}
                    error={!!fieldState.error}
                    helperText={fieldState.error ? fieldState.error.message : null}
                  />
                }
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <input {...register('file', { required: 'Wymagany plik w formacie .xlsx lub .xls' })}
                     onChange={handleSubmit}
                     type="file" />
              {errors.file && <p style={{color:'red'}}>{errors.file.message}</p>}
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