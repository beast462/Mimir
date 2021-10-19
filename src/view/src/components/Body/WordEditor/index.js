import { Box, Divider, Grow } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React from 'react';
import { connect } from 'react-redux';
import { maxWidth } from '../../../const';
import ConfirmSection from './ConfirmSection';
import DefinitionSection from './DefinitionSection';
import WordSection from './WordSection';

const useStyles = makeStyles(theme => ({
  root: {
    backgroundColor: theme.palette.background.paper,
    zIndex: theme.zIndex.tooltip + theme.zIndex.snackbar + theme.zIndex.modal,
  },
}));

const connector = connect(
  state => ({
    editing: state.app.editingWord,
  }),
  {},
);

function WordEditor(props) {
  const classes = useStyles();

  const submit = event => {
    const form = event.target;
  };

  return (
    <Grow in={props.editing}>
      <Box
        width="100vw"
        height="100vh"
        position="fixed"
        left="0"
        top="0"
        display={props.editing ? 'flex' : 'none'}
        flexDirection="row"
        justifyContent="center"
        className={classes.root}>
        <Box
          width="100%"
          maxWidth={maxWidth}
          height="100%"
          overflow="hidden"
          display="flex"
          flexDirection="column"
          component="form"
          onSubmit={submit}>
          <WordSection />
          <Divider />
          <DefinitionSection />
          <Divider />
          <ConfirmSection />
        </Box>
      </Box>
    </Grow>
  );
}

export default connector(WordEditor);
