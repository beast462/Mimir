import React, { useEffect } from 'react';
import { Box, ListItemText, TextField } from '@mui/material';
import { makeStyles } from '@mui/styles';

const useStyles = makeStyles(theme => ({
  item: {
    paddingLeft: theme.spacing(1),
  },
}));

function ExampleEditor(props) {
  const { example, editing, engRef, vieRef } = props;
  const classes = useStyles();

  return editing ? (
    <Box
      display="flex"
      flexDirection="row"
      justifyContent="flex-start"
      alignItems="flex-start">
      <TextField
        label="English"
        name="eng"
        inputRef={engRef}
        defaultValue={example.english}
        multiline
        fullWidth
      />
      <Box width="1rem" />
      <TextField
        label="Tiếng Việt"
        name="vie"
        inputRef={vieRef}
        defaultValue={example.vietnamese}
        multiline
        fullWidth
      />
    </Box>
  ) : (
    <ListItemText
      className={classes.item}
      primary={example.english}
      secondary={example.vietnamese}
    />
  );
}

export default ExampleEditor;
