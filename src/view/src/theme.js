import { createTheme } from '@mui/material';
import { blue, green } from '@mui/material/colors';

const theme = createTheme({
  palette: {
    primary: {
      light: blue[600],
      main: blue[700],
      dark: blue[800],
    },
    success: {
      light: green[500],
      main: green[600],
      dark: green[700],
    },
  },
});

export default theme;
