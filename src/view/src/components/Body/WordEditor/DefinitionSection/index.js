import { Box, Button } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React, { useState } from 'react';
import { connect } from 'react-redux';
import Definition from './Definition';
import addDefinition from '@store/actions/editor/addDefinition';

const useStyles = makeStyles(() => ({
  root: {
    paddingTop: '0.5rem',
    paddingBottom: '0.5rem',
    flex: 1,
    WebkitFlex: 1,
    msFlex: 1,
    flexGrow: 1,
    WebkitFlexGrow: 1,
  },
  defs: {
    flex: 1,
    WebkitFlex: 1,
    msFlex: 1,
    flexGrow: 1,
    WebkitFlexGrow: 1,
  },
}));

const connector = connect(
  state => ({
    definitions: state.editor.definitions,
  }),
  {
    addDefinition,
  },
);

function DefinitionSection(props) {
  const classes = useStyles();
  const [hoveringDefinition, setHoveringDefinition] = useState(-1);

  return (
    <Box
      width="100%"
      display="flex"
      flexDirection="column"
      overflow="hidden"
      className={classes.root}>
      <Button
        fullWidth
        variant="contained"
        size="large"
        color="primary"
        onClick={props.addDefinition}>
        add new definition
      </Button>

      <Box height="2rem" />

      <Box
        width="100%"
        display="block"
        overflow="auto"
        className={classes.defs}
        component="div"
        onMouseLeave={() => setHoveringDefinition(-1)}>
        {props.definitions.map((def, index) => (
          <Definition
            key={`def/${def.id}`}
            definition={def}
            onMouseEnter={() => setHoveringDefinition(index)}
            hovering={hoveringDefinition === index}
          />
        ))}
      </Box>
    </Box>
  );
}

export default connector(DefinitionSection);
