import React from 'react';
import Check from '@mui/icons-material/Check';
import {
  Box,
  CircularProgress,
  DialogContentText,
  Typography,
} from '@mui/material';

function AFEDBody(props) {
  switch (props.scene) {
    case 0:
      return (
        <DialogContentText>
          It seems like you don't have any data in database yet. Would you like
          to fetch our data from recommended source?
        </DialogContentText>
      );

    case 1:
      return (
        <Box
          width="100%"
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center">
          <Box height="2rem" />

          <CircularProgress
            size={~~(Math.min(innerWidth, innerHeight) * 0.1)}
          />

          <Box height="2rem" />

          <Typography>Fetching data</Typography>
          <Typography variant="caption">
            Be patient, this won't take long
          </Typography>
        </Box>
      );

    case 2:
      return (
        <Box
          width="100%"
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center">
          <Box height="2rem" />

          <Check fontSize="large" color="success" />

          <Box height="2rem" />

          <Typography>Your data is ready</Typography>
        </Box>
      );

    default:
      return null;
  }
}

export default AFEDBody;
