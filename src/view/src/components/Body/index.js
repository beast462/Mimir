import React from 'react';
import { Box } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { maxWidth } from '../../const';
import Greeting from './Greeting';
import WordEditor from './WordEditor';
import Explanation from './Explanation';

const useStyles = makeStyles(() => ({
  root: {
    flexGrow: 1,
    WebkitFlexGrow: 1,
    flex: 1,
    WebkitFlex: 1,
  },
}));

function Body() {
  const classes = useStyles();

  return (
    <Box
      className={classes.root}
      maxWidth={maxWidth}
      width="100%"
      overflow="hidden">
      <Greeting />
      <Explanation />
      <WordEditor />
    </Box>
  );
}

export default Body;
