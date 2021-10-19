import _toPairs from 'lodash/toPairs';
import {
  Box,
  Chip,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from '@mui/material';
import React, { useState } from 'react';
import { connect } from 'react-redux';
import { makeStyles } from '@mui/styles';
import { blue } from '@mui/material/colors';
import clsx from 'clsx';

const useStyles = makeStyles(theme => ({
  selected: {
    backgroundColor: `${blue[50]}!important`,
    fontWeight: 'bolder',
  },
  menuItem: {
    backgroundColor: 'transparent',
  },
  chip: {
    marginLeft: theme.spacing(0.5),
    marginBottom: theme.spacing(0.25),
  },
}));

const connector = connect(
  state => ({
    typeRefs: _toPairs(state.app.wordTypeRefs),
  }),
  {},
);

function DefinitionEditor(props) {
  const classes = useStyles();
  const [types, setTypes] = useState(props.type);

  const handleChange = event => {
    const selection = event.target.value;

    setTypes(types ^ selection);
  };

  return (
    <>
      <TextField
        defaultValue={props.definition}
        variant="outlined"
        label="Definition"
        inputRef={props.defRef}
      />

      <Box width="1rem" />

      <FormControl>
        <InputLabel>Word types</InputLabel>
        <Select
          value={types}
          label="Word types"
          notched
          onChange={handleChange}
          inputRef={props.typesRef}
          renderValue={selected => (
            <Box display="flex" flexWrap="wrap" width="200px" maxWidth="100%">
              {props.typeRefs
                .filter(([tr]) => selected & (tr | 0))
                .map(([code, name]) => (
                  <Chip
                    key={`c/typ/${code}`}
                    label={name}
                    variant="outlined"
                    className={classes.chip}
                  />
                ))}
            </Box>
          )}
          MenuProps={{
            style: {
              zIndex: 1e6,
            },
          }}>
          {props.typeRefs
            .filter(([c]) => c)
            .map(([code, name]) => (
              <MenuItem
                key={`e/typ/${code}`}
                value={code | 0}
                className={clsx(classes.menuItem, {
                  [classes.selected]: types & code,
                })}>
                {name}
              </MenuItem>
            ))}
        </Select>
      </FormControl>
    </>
  );
}

export default connector(DefinitionEditor);
