import React from 'react';
import { List, ListItem, ListItemText } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { connect } from 'react-redux';
import showWordExplanation from '@store/actions/app/showWordExplanation';

const useStyles = makeStyles(() => ({
  item: {
    fontSize: '1.3rem',
  },
}));

const connector = connect(
  state => ({
    suggestions: state.search.suggestions,
  }),
  { showWordExplanation },
);

function SuggestionList(props) {
  const classes = useStyles();

  return (
    <List dense>
      {props.suggestions.map(suggestion => (
        <ListItem
          key={`sgg/${suggestion.id}`}
          className={classes.item}
          button
          onClick={() => props.showWordExplanation(suggestion.id)}>
          <ListItemText primary={suggestion.content} />
        </ListItem>
      ))}
    </List>
  );
}

export default connector(SuggestionList);
