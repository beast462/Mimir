import {
  Typography,
  List,
  ListItem,
  ListItemText,
  Tooltip,
  Box,
  Chip,
} from '@mui/material';
import _toPairs from 'lodash/toPairs';
import { makeStyles } from '@mui/styles';
import React from 'react';
import { connect } from 'react-redux';
import showWordExplanation from '@store/actions/app/showWordExplanation';

const useStyles = makeStyles(() => ({
  segment: {
    width: '100%',
    padding: '1rem',
    marginTop: '1rem',
    marginBottom: '1rem',
    backgroundColor: '#fefefe',
  },
  relation: {
    marginLeft: '1rem',
  },
}));

const connector = connect(
  state => ({
    typeRefs: state.app.wordTypeRefs,
  }),
  {
    showWordExplanation,
  },
);

function Definition(props) {
  const { definition } = props;
  const classes = useStyles();

  const wordTypes = _toPairs(props.typeRefs)
    .filter(tr => definition.wordType & (tr[0] | 0))
    .map(tr => tr[1]);

  return (
    <div className={classes.segment}>
      <Typography variant="h5">{definition.definition}</Typography>
      {wordTypes.length === 0 ? null : (
        <Tooltip
          title={wordTypes.length > 1 ? wordTypes.join(', ') : ''}
          placement="bottom-end">
          <Typography variant="body1" align="right">
            [{wordTypes[0]}
            {wordTypes.length > 1 ? <sup>{wordTypes.length}</sup> : null}]
          </Typography>
        </Tooltip>
      )}

      {definition.examples.length !== 0 ? (
        <>
          <Typography>Ví dụ</Typography>
          <List>
            {definition.examples.map(ex => (
              <ListItem key={`v/exm/${definition.id}/${ex.id}`}>
                <ListItemText primary={ex.english} secondary={ex.vietnamese} />
              </ListItem>
            ))}
          </List>
        </>
      ) : null}

      {definition.relations.length !== 0 ? (
        <Box display="flex" flexDirection="row" flexWrap="wrap">
          <Typography>See also: </Typography>
          {definition.relations.map(rel => (
            <Chip
              key={`v/rel/${rel.id}`}
              className={classes.relation}
              size="small"
              label={rel.content}
              clickable
              onClick={() => props.showWordExplanation(rel.wordRef)}
            />
          ))}
        </Box>
      ) : null}
    </div>
  );
}

export default connector(Definition);
