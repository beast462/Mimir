import Add from '@mui/icons-material/Add';
import {
  Button,
  IconButton,
  InputAdornment,
  InputBase,
  Tooltip,
} from '@mui/material';
import { makeStyles } from '@mui/styles';
import Search from '@mui/icons-material/Search';
import clsx from 'clsx';
import { connect } from 'react-redux';
import _debounce from 'lodash/debounce';
import typeSearch from '@store/actions/search/typeSearch';
import openWordEditor from '@store/actions/app/openWordEditor';
import SuggestionList from './SuggestionList';
import React, { useCallback } from 'react';
import fetchRecent from '@store/actions/search/fetchRecent';
import resetEditor from '@store/actions/editor/resetEditor';
import { SuggestionUses } from '~/const';

const useStyles = makeStyles(theme => ({
  input: {
    width: '100%',
    fontSize: '1.5rem',
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    lineHeight: '1.5rem',
  },
  inputContainer: {
    width: '100%',
  },
  suggestion: {
    width: '100%',
    display: 'none',
    '&.has-suggestion': {
      display: 'block',
    },
  },
}));

const connector = connect(
  state => ({
    hasSuggestions:
      state.search.suggestions.length !== 0 &&
      state.search.suggestionUse === SuggestionUses.SEARCH,
  }),
  {
    typeSearch,
    fetchRecent,
    resetEditor,
    openWordEditor,
  },
);

function SearchField(props) {
  const classes = useStyles();

  const debouncedSearch = useCallback(
    _debounce(
      event => {
        props.typeSearch(event.target.value);
      },
      300,
      {
        maxWait: Infinity,
        leading: false,
        trailing: true,
      },
    ),
    [props.typeSearch],
  );

  return (
    <>
      <InputBase
        classes={{
          input: classes.input,
          root: classes.inputContainer,
        }}
        placeholder="Type here to search"
        onKeyDown={debouncedSearch}
        onFocus={() => props.fetchRecent()}
        startAdornment={
          <InputAdornment position="start">
            <Tooltip title="Add new word">
              <Button
                variant="contained"
                color="info"
                onClick={() => [props.resetEditor(), props.openWordEditor()]}>
                <Add />
              </Button>
            </Tooltip>
          </InputAdornment>
        }
        endAdornment={
          <InputAdornment position="end">
            <Tooltip title="Search">
              <IconButton size="medium">
                <Search />
              </IconButton>
            </Tooltip>
          </InputAdornment>
        }
      />

      <div
        className={clsx(classes.suggestion, {
          'has-suggestion': props.hasSuggestions,
        })}>
        <SuggestionList />
      </div>
    </>
  );
}

export default connector(SearchField);
