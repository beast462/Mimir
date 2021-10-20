import React from 'react';
import { Box, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { connect } from 'react-redux';
import book from './book.png';

const useStyles = makeStyles(theme => ({
  img: {
    width: '100%',
    margin: 0,
  },
  container: {
    '& > *': {
      pointerEvents: 'none',
    },
  },
  greetingText: {
    fontSize: '1.4vw',
  },
}));

const connector = connect(
  state => ({
    viewingSomething: state.app.viewingWord !== -1,
  }),
  {},
);

const flexCommon = {
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
};

function Greeting(props) {
  const classes = useStyles();

  return (
    <Box
      display={props.viewingSomething ? 'none' : 'flex'}
      className={classes.container}
      {...flexCommon}
      width="100%"
      height="100%">
      <Box
        {...flexCommon}
        className="noselect"
        style={{ backgroundColor: '#f7f7f7' }}
        display="flex"
        width="calc(min(1vw, 1vh) * 60)"
        height="calc(min(1vw, 1vh) * 60)"
        borderRadius="100vw"
        overflow="hidden"
        alignItems="center"
        justifyContent="center">
        <Box height="2rem"></Box>
        <img className={classes.img} src={book} alt="" />
        <Box height="2rem">
          <Typography
            align="center"
            variant="h6"
            className={classes.greetingText}
            color="textSecondary">
            Let's search something!
          </Typography>
        </Box>
      </Box>
    </Box>
  );
}

export default connector(Greeting);
