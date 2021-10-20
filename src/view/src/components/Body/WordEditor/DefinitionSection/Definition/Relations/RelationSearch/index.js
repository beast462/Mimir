import _debounce from 'lodash/debounce';
import { InputBase, List, ListItem, Paper } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React, { useCallback, useState } from 'react';
import { connect } from 'react-redux';
import addRelation from '@store/actions/editor/addRelation';

const useStyles = makeStyles(() => ({
  root: {
    width: '100%',
    maxWidth: '250px',
  },
  input: {
    width: '100%',
    padding: '0.25rem 1rem',
  },
}));

const connector = connect(() => ({}), {
  addRelation,
});

function calculateFitInInnerHeight() {
  return ((innerHeight * 0.3) / 32) | 0;
}

function RelationSearch(props) {
  const classes = useStyles();
  const [suggestions, setSuggestions] = useState([]);

  const debouncedSearch = useCallback(
    _debounce(
      event => {
        setSuggestions(
          window.bridge.advanceSearch(
            event.target.value,
            calculateFitInInnerHeight(),
          ),
        );
      },
      300,
      {
        maxWait: Infinity,
        leading: false,
        trailing: true,
      },
    ),
    [setSuggestions],
  );

  const selectSuggestion = suggestion => {
    setSuggestions([]);
    props.addRelation(props.definitionId, suggestion);
  };

  return (
    <Paper
      elevation={suggestions.length === 0 ? 0 : 2}
      className={classes.root}>
      <InputBase
        placeholder="Add related word"
        className={classes.input}
        onKeyDown={debouncedSearch}
      />

      <List>
        {suggestions.map(suggestion => (
          <ListItem
            key={`e/rel/${suggestion.id}`}
            button
            onClick={() => selectSuggestion(suggestion)}>
            {suggestion.content}
          </ListItem>
        ))}
      </List>
    </Paper>
  );
}

export default connector(RelationSearch);
