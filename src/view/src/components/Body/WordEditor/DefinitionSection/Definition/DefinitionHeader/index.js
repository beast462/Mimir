import _toPairs from 'lodash/toPairs';
import Check from '@mui/icons-material/Check';
import ChevronRight from '@mui/icons-material/ChevronRight';
import Close from '@mui/icons-material/Close';
import Delete from '@mui/icons-material/Delete';
import Edit from '@mui/icons-material/Edit';
import Add from '@mui/icons-material/Add';
import ExpandMore from '@mui/icons-material/ExpandMore';
import {
  Box,
  Button,
  ButtonGroup,
  IconButton,
  Tooltip,
  Typography,
} from '@mui/material';
import { connect } from 'react-redux';
import { makeStyles } from '@mui/styles';
import React, { useRef, useState } from 'react';
import DefinitionEditor from './DefinitionEditor';
import deleteDefinition from '@store/actions/editor/deleteDefinition';
import addExample from '@store/actions/editor/addExample';
import editDefinition from '@store/actions/editor/editDefinition';
import clsx from 'clsx';

const useStyles = makeStyles(() => ({
  root: {
    '&:hover .hover-btn-group': {
      display: 'inline-flex',
    },
  },
  definition: {
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    overflow: 'hidden',
  },
  wordType: {
    whiteSpace: 'nowrap',
  },
  hoveringBtnGroup: {
    display: 'none',
    position: 'absolute',
    right: '1rem',
  },
  tooltip: {
    zIndex: 1e9,
  },
}));

const connector = connect(
  state => ({
    typeRefs: state.app.wordTypeRefs,
  }),
  {
    deleteDefinition,
    editDefinition,
    addExample,
  },
);

function DefinitionHeader(props) {
  const classes = useStyles();
  const [editing, setEditing] = useState(false);
  const inputRefs = {
    def: useRef(),
    types: useRef(),
  };

  const wordTypes = _toPairs(props.typeRefs)
    .filter(tr => props.type & (tr[0] | 0))
    .map(tr => tr[1]);

  const acceptButtonClick = () => {
    if (!editing) return setEditing(true);

    props.editDefinition(
      props.definitionId,
      inputRefs.def.current.value,
      inputRefs.types.current.value,
    );

    setEditing(false);
  };

  const rejectButtonClick = () => {
    if (editing) return setEditing(false);

    props.deleteDefinition(props.definitionId);
  };

  return (
    <Box
      display="flex"
      position="relative"
      width="100%"
      paddingBottom="0.5rem"
      flexDirection="row"
      alignItems={editing ? 'flex-start' : 'flex-end'}
      component="div"
      paddingRight="1rem"
      className={classes.root}
      onMouseEnter={props.onMouseEnter}>
      <IconButton onClick={() => props.setOpen(!props.open)}>
        {props.open ? <ExpandMore /> : <ChevronRight />}
      </IconButton>

      {editing ? (
        <DefinitionEditor
          type={props.type}
          definition={props.definition}
          definitionId={props.definitionId}
          defRef={inputRefs.def}
          typesRef={inputRefs.types}
        />
      ) : (
        <>
          <Typography variant="h4" className={classes.definition}>
            {props.definition}
          </Typography>

          {wordTypes.length === 0 ? null : (
            <>
              <Box width="1rem" />

              <Tooltip
                classes={{
                  popper: classes.tooltip,
                }}
                title={wordTypes.length > 1 ? wordTypes.join(', ') : ''}
                placement="bottom-end">
                <Typography variant="h6" className={classes.wordType}>
                  [{wordTypes[0]}
                  {wordTypes.length > 1 ? <sup>{wordTypes.length}</sup> : null}]
                </Typography>
              </Tooltip>
            </>
          )}
        </>
      )}

      {props.hovering ? (
        <ButtonGroup
          variant="contained"
          size="small"
          className={clsx(classes.hoveringBtnGroup, 'hover-btn-group')}>
          <Tooltip title="Add example">
            <Button
              color="success"
              onClick={() => props.addExample(props.definitionId)}>
              <Add />
            </Button>
          </Tooltip>

          <Tooltip title={editing ? 'Cancel' : 'Delete definition'}>
            <Button color="error" onClick={rejectButtonClick}>
              {editing ? <Close /> : <Delete />}
            </Button>
          </Tooltip>

          <Tooltip title={editing ? 'Accept' : 'Edit definition'}>
            <Button color="primary" onClick={acceptButtonClick}>
              {editing ? <Check /> : <Edit />}
            </Button>
          </Tooltip>
        </ButtonGroup>
      ) : null}
    </Box>
  );
}

export default connector(DefinitionHeader);
