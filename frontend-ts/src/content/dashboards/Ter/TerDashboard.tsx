import { useState } from 'react';
import { Button, CircularProgress, Divider, Grid, TextField } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { Controller, useForm } from 'react-hook-form';
import { sendNewTer } from './actions';
import BranchSearch from '../../../components/BranchSearch';
import DateSelector from '../../../components/DateSelector';

interface FormInputs {
  companyName: string;
  contractNumber: string;
  roadLength: number;
  file: FileList;
}

function TerDashboard() {
  const [loading, setLoading] = useState(false);
  const [selectedBranch, setSelectedBranch] = useState<string>('');
  const [selectedRegion, setSelectedRegion] = useState<string[]>([]);
  const [selectedSection, setSelectedSection] = useState<string[]>([]);

  const { control, register, formState: { errors }, handleSubmit, reset } = useForm<FormInputs>({
    defaultValues: {
      companyName: '',
      contractNumber: '',
      roadLength: 0,
      file: undefined
    }
  });

  const [selectedStartDate, setSelectedStartDate] = useState<Date | null>(null);
  const [formattedStartDate, setFormattedStartDate] = useState<string | null>(null);
  const [selectedEndDate, setSelectedEndDate] = useState<Date | null>(null);
  const [formattedEndDate, setFormattedEndDate] = useState<string | null>(null);

  const onSubmit = (data: FormInputs) => {
    if (!data.file[0]) return;

    sendNewTer(
      data.file[0],
      data.companyName,
      data.contractNumber,
      selectedBranch,
      selectedRegion,
      selectedSection,
      formattedStartDate,
      formattedEndDate,
      data.roadLength
    )
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

  if (loading) {
    return (
      <Grid sx={{ display: 'flex', justifyContent: 'center' }}>
        <CircularProgress size={100} />
      </Grid>
    );
  }

  return (
    <Grid>
      <form onSubmit={handleSubmit(onSubmit)}>
        <h2>Formularz dodawania TER</h2>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <Controller
              name="companyName"
              control={control}
              rules={{ required: 'Nazwa firmy jest wymagana' }}
              render={({ field, fieldState }) => (
                <TextField
                  fullWidth
                  {...field}
                  placeholder="Nazwa Firmy"
                  error={!!fieldState.error}
                  helperText={fieldState.error ? fieldState.error.message : null}
                />
              )}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <Controller
              name="contractNumber" 
              control={control}
              rules={{ required: 'Nr umowy jest wymagany' }}
              render={({ field, fieldState }) => (
                <TextField
                  fullWidth
                  {...field}
                  placeholder="Nr umowy"
                  error={!!fieldState.error}
                  helperText={fieldState.error ? fieldState.error.message : null}
                />
              )}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DateSelector
                selectedDate={selectedStartDate}
                setSelectedDate={setSelectedStartDate}
                setFormattedDate={setFormattedStartDate}
                label="Wybierz date początkową"
              />
              <DateSelector
                selectedDate={selectedEndDate}
                setSelectedDate={setSelectedEndDate}
                setFormattedDate={setFormattedEndDate}
                label="Wybierz datę końcową"
              />
            </LocalizationProvider>
          </Grid>

          <Grid item xs={12}>
            <Divider />
          </Grid>

          <Grid item xs={12} sm={6}>
            <Controller
              name="roadLength"
              control={control}
              rules={{
                required: 'To pole jest wymagane'
              }}
              render={({ field: { onChange, value }, fieldState: { error } }) => (
                <TextField
                  sx={{ p: 1 }}
                  fullWidth
                  label="Długość kilometrów objętych kontraktem"
                  value={value}
                  onChange={onChange}
                  error={!!error}
                  helperText={error ? error.message : ''}
                  inputProps={{ type: 'number', min: 0 }}
                />
              )}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <BranchSearch
              selectedBranch={selectedBranch}
              selectedRegion={selectedRegion}
              selectedSection={selectedSection}
              setSelectedBranch={setSelectedBranch}
              setSelectedRegion={setSelectedRegion}
              setSelectedSection={setSelectedSection}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <input
              {...register('file', { required: 'Wymagany plik w formacie .xlsx lub .xls' })}
              type="file"
            />
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
}

export default TerDashboard;
