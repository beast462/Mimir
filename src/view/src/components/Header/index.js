import { AppBar, Box, Paper } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React, { useEffect, useRef } from 'react';
import { connect } from 'react-redux';
import { maxWidth, SuggestionUses } from '~/const';
import focusSearch from '@store/actions/search/focusSearch';
import unfocusSearch from '@store/actions/search/unfocusSearch';
import SearchField from './SearchField';

const useStyles = makeStyles(theme => ({
  root: {
    paddingTop: theme.spacing(1),
    paddingBottom: theme.spacing(1),
    height: theme.spacing(7.5),
  },
  searchRegion: {
    position: 'static',
  },
  inputGroup: {
    width: '100%',
  },
  inputContainer: {
    position: 'absolute',
    maxWidth: maxWidth,
    width: '80vw',
    marginLeft: `calc(-0.5 * min(80vw, ${maxWidth}px))`,
    transition: theme.transitions.easing.easeOut,
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    zIndex: theme.zIndex.tooltip - theme.zIndex.mobileStepper,
  },
}));

const connector = connect(
  state => ({
    suggestionUse: state.search.suggestionUse,
  }),
  {
    focusSearch,
    unfocusSearch,
  },
);

const staticRefs = {};

function Header(props) {
  const classes = useStyles();
  const inputContainerRef = useRef();

  staticRefs.focusSearch = props.focusSearch;
  staticRefs.unfocusSearch = props.unfocusSearch;
  staticRefs.container = inputContainerRef;
  staticRefs.suggestionUse = props.suggestionUse;

  useEffect(() => {
    function handle(event) {
      if (staticRefs.container.current) {
        const container = staticRefs.container.current;

        if (container.contains(event.target)) {
          if (staticRefs.suggestionUse === SuggestionUses.NONE)
            staticRefs.focusSearch();
        } else {
          if (staticRefs.suggestionUse !== SuggestionUses.NONE)
            staticRefs.unfocusSearch();
        }
      }
    }

    document.addEventListener('click', handle);

    return () => {
      document.removeEventListener('click', handle);
    };
  }, []);

  return (
    <AppBar color="primary" className={classes.root} position="static">
      <Box
        width="100%"
        height="100%"
        display="flex"
        flexDirection="row"
        justifyContent="center">
        <div className={classes.searchRegion}>
          <Paper ref={inputContainerRef} className={classes.inputContainer}>
            <SearchField />
          </Paper>
        </div>
      </Box>
    </AppBar>
  );
}

export default connector(Header);
