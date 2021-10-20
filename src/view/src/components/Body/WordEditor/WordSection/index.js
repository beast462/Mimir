import { Box, InputBase } from '@mui/material';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import React from 'react';
import { connect } from 'react-redux';
import editWord from '@store/actions/editor/editWord';

const useStyles = makeStyles(() => ({
  input: {
    fontSize: '1.5rem',
  },
  word: {},
  pronunciation: {
    width: '13rem',
  },
}));

const connector = connect(
  state => ({
    word: state.editor.content,
    pronunciation: state.editor.pronunciation,
  }),
  {
    editWord,
  },
);

function WordSection(props) {
  const classes = useStyles();

  return (
    <Box
      width="100%"
      display="flex"
      flexDirection="row"
      justifyContent="space-between">
      <InputBase
        placeholder="Word"
        value={props.word}
        onChange={event => props.editWord('content', event.target.value)}
        classes={{ input: clsx(classes.input, classes.word) }}
      />
      <Box display="flex" flexDirection="row" justifyContent="center">
        <h3 className={classes.input}>/</h3>
        <InputBase
          placeholder="prəˌnənsēˈāSH(ə)n"
          value={props.pronunciation}
          onChange={event =>
            props.editWord('pronunciation', event.target.value)
          }
          classes={{ input: clsx(classes.input, classes.pronunciation) }}
        />
        <h3 className={classes.input}>/</h3>
      </Box>
    </Box>
  );
}

export default connector(WordSection);
