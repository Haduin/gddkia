import React from 'react';
import { Button, Grid, Stack, TextField } from '@mui/material';
import { Controller, useForm } from 'react-hook-form';
import { styled } from '@mui/material/styles';
import { sendNewTer } from './actions';


const Item = styled('div')(({ theme, border = false }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
  borderStyle: border ? 'solid' : 'none'
}));

const TerDashboard = () => {
  const { control, register, formState: { errors }, handleSubmit } = useForm({
    defaultValues: {
      companyName: '',
      contractNumber: '',
      regionName: '',
      branchName: '',
      // startDate: '',
      // endDate: '',
      file: ''
    }
  });

  const onSubmit = (data) => {
    sendNewTer(data.file[0], data.companyName, data.contractNumber, data.regionName, data.branchName);
  };


  return (
    <Grid>
      <Item border={true}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Item>
            <h2>Formularz dodawania nowego TERA</h2>
            <Stack spacing={0.1}>
              <Item>
                <Controller
                  name="companyName"
                  control={control}
                  rules={{ required: 'Nazwa firmy jest wymagana' }}
                  render={({ field, fieldState }) =>
                    <TextField
                      {...field}
                      placeholder="Nazwa Firmy"
                      error={!!fieldState.error}
                      helperText={fieldState.error ? fieldState.error.message : null}
                    />
                  }
                />
              </Item>
              <Item>
                <Controller
                  name="contractNumber"
                  control={control}
                  rules={{ required: 'Nr umowy jest wymagany' }}
                  render={({ field, fieldState }) =>
                    <TextField
                      {...field}
                      placeholder="Nr umowy"
                      error={!!fieldState.error}
                      helperText={fieldState.error ? fieldState.error.message : null}
                    />
                  }
                />
              </Item>
              {/*//TODO change it to custom Select compoenent with calls to backend */}
              <Item>
                <Controller
                  name="regionName"
                  control={control}
                  rules={{ required: 'Region musi być wybrany' }}
                  render={({ field, fieldState }) =>
                    <TextField
                      {...field}
                      placeholder="Region"
                      error={!!fieldState.error}
                      helperText={fieldState.error ? fieldState.error.message : null}
                    />
                  }
                />
              </Item>
              <Item>
                <Controller
                  name="branchName"
                  control={control}
                  rules={{ required: 'Oddział musi być wybrany' }}
                  render={({ field, fieldState }) =>
                    <TextField
                      {...field}
                      placeholder="Oddział"
                      error={!!fieldState.error}
                      helperText={fieldState.error ? fieldState.error.message : null}
                    />
                  }
                />
              </Item>


              <Item>
                <input {...register('file', { required: 'Wymagany plik w formacie .xlsx lub .xls' })}
                       onChange={handleSubmit}
                       type="file" />
                {errors.file && <p>{errors.file.message}</p>}
              </Item>
              <Button variant="contained" type="submit">
                Zapisz
              </Button>
            </Stack>
          </Item>
        </form>
      </Item>
    </Grid>


  );
};

export default TerDashboard;