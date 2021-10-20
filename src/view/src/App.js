import React from 'react';
import { Box, CssBaseline, ThemeProvider } from '@mui/material';
import { Provider } from 'react-redux';
import theme from './theme';
import store from './store';
import Header from './components/Header';
import Body from './components/Body';
import SplashScreen from './components/SplashScreen';
import SharedDialog from './components/SharedDialog';

function App() {
  return (
    <Provider store={store}>
      <CssBaseline />

      <ThemeProvider theme={theme}>
        <Box
          display="flex"
          flexDirection="column"
          alignItems="center"
          width="100vw"
          height="100vh"
          overflow="hidden">
          <SplashScreen />
          <Header />
          <Body />
          <SharedDialog />
        </Box>
      </ThemeProvider>
    </Provider>
  );
}

export default App;
