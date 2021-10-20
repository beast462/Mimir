import { Box, Collapse, Divider, List } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React, { useEffect, useState } from 'react';
import DefinitionHeader from './DefinitionHeader';
import Example from './Example';
import Relations from './Relations';

const useStyles = makeStyles(theme => ({
  root: {
    paddingTop: theme.spacing(1),
    paddingBottom: theme.spacing(1),
  },
  sub: {
    paddingLeft: theme.spacing(5),
  },
}));

const staticRefs = {
  open: true,
  setDisposed: null,
};

function Definition(props) {
  const classes = useStyles();
  const [open, setOpen] = useState(true);
  const [disposed, setDisposed] = useState(false);
  const [hoveringExample, setHoveringExample] = useState(-1);

  staticRefs.open = open;
  staticRefs.setDisposed = setDisposed;

  useEffect(() => {
    if (open === false)
      setTimeout(() => {
        if (staticRefs.open === false) {
          staticRefs.setDisposed(true);
        }
      }, 400);
    else {
      if (disposed) setDisposed(false);
    }
  }, [open]);

  return (
    <Box
      display="flex"
      width="100%"
      flexDirection="column"
      className={classes.root}>
      <DefinitionHeader
        open={open}
        setOpen={setOpen}
        definition={props.definition.definition}
        definitionId={props.definition.id}
        type={props.definition.wordType}
        onMouseEnter={props.onMouseEnter}
        hovering={props.hovering}
      />

      <Divider />

      <Collapse in={open} className={classes.sub} timeout={400}>
        {disposed ? null : (
          <>
            <List component="div" onMouseLeave={() => setHoveringExample(-1)}>
              {props.definition.examples.map((example, index) => (
                <Example
                  key={`exm/${example.id}`}
                  example={example}
                  hovering={hoveringExample === index}
                  onMouseEnter={() => setHoveringExample(index)}
                />
              ))}
            </List>

            <Relations
              relations={props.definition.relations}
              definitionId={props.definition.id}
            />
          </>
        )}
      </Collapse>
    </Box>
  );
}

export default Definition;
