import {
  Box,
  Chip,
  InputAdornment,
  InputBase,
  Paper,
  Typography,
} from '@mui/material';
import { makeStyles } from '@mui/styles';
import React from 'react';
import { connect } from 'react-redux';
import RelationSearch from './RelationSearch';
import deleteRelation from '@store/actions/editor/deleteRelation';

const useStyles = makeStyles(() => ({
  title: {
    paddingTop: '0.5rem',
  },
  chip: {
    marginRight: '0.25rem',
  },
}));

const connector = connect(() => ({}), { deleteRelation });

function Relations(props) {
  const classes = useStyles();

  return (
    <Box width="100%" paddingLeft="0.5rem" paddingRight="1rem">
      <Box
        display="flex"
        flexDirection="row"
        alignItems="flex-start"
        padding="0.4rem">
        <Typography className={classes.title}>See also:</Typography>

        <Box width="1rem" />

        <Box display="flex" flexDirection="column" flex="1" flexGrow="1">
          <RelationSearch definitionId={props.definitionId} />

          <Box display="block" width="100%" paddingTop="0.5rem">
            {props.relations.map((relation, index) => (
              <Chip
                key={`rel/${index}`}
                variant="outlined"
                label={relation.content}
                className={classes.chip}
                onDelete={() =>
                  props.deleteRelation(props.definitionId, relation.id)
                }
              />
            ))}
          </Box>
        </Box>
      </Box>
    </Box>
  );
}

export default connector(Relations);
