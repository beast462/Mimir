import { Box, CircularProgress, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import bridgeInitializedAction from '@store/actions/unknown/bridgeInitializedAction';

const useStyles = makeStyles(theme => ({
  root: {
    backgroundColor: 'white',
    zIndex: theme.zIndex.tooltip,
  },
}));

const connector = connect(() => ({}), {
  bridgeInitializedAction,
});

function SplashScreen(props) {
  const classes = useStyles();
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    const check = () => {
      if (typeof window.bridge === 'object') {
        props.bridgeInitializedAction();
        return setInitialized(true);
      }

      requestAnimationFrame(check);
    };

    requestAnimationFrame(check);
  }, []);

  return initialized ? null : (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      width="100vw"
      height="100vh"
      position="fixed"
      top="0"
      left="0"
      className={classes.root}>
      <CircularProgress size={~~(Math.min(innerWidth, innerHeight) * 0.2)} />
      <Box height="4rem" />
      <Typography variant="h4" className="noselect">
        Initializing application bridge
      </Typography>
    </Box>
  );
}

export default connector(SplashScreen);
