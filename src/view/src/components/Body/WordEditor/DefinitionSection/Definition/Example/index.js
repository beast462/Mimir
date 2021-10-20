import Close from '@mui/icons-material/Close';
import Check from '@mui/icons-material/Check';
import Edit from '@mui/icons-material/Edit';
import Delete from '@mui/icons-material/Delete';
import {
  Button,
  ButtonGroup,
  ListItemSecondaryAction,
  Tooltip,
} from '@mui/material';
import React, { useRef, useState } from 'react';
import { makeStyles } from '@mui/styles';
import { connect } from 'react-redux';
import deleteExample from '@store/actions/editor/deleteExample';
import editExample from '@store/actions/editor/editExample';
import ExampleEditor from './ExampleEditor';

const useStyles = makeStyles(theme => ({
  root: {
    position: 'relative',
    padding: theme.spacing(1, 2, 1, 1),
    '& .hover-btn-group': {
      display: 'none',
    },
    '&:hover .hover-btn-group': {
      display: 'inline-flex',
    },
  },
}));

const connector = connect(() => ({}), {
  deleteExample,
  editExample,
});

function Example(props) {
  const { example } = props;
  const classes = useStyles();
  const [editing, setEditing] = useState(false);
  const inputRefs = {
    eng: useRef(),
    vie: useRef(),
  };

  const acceptButtonClick = () => {
    if (!editing) return setEditing(true);

    props
      .editExample(
        example.id,
        inputRefs.eng.current?.value,
        inputRefs.vie.current?.value,
      )
      .then(() => setEditing(false));
  };

  const rejectButtonClick = () => {
    if (editing) return setEditing(false);

    props.deleteExample(example.id);
  };

  return (
    <li onMouseEnter={props.onMouseEnter} className={classes.root}>
      <ExampleEditor
        example={example}
        editing={editing}
        engRef={inputRefs.eng}
        vieRef={inputRefs.vie}
      />

      {props.hovering ? (
        <ListItemSecondaryAction>
          <ButtonGroup
            variant="contained"
            size="small"
            className="hover-btn-group">
            <Tooltip title={editing ? 'Cancel' : 'Delete example'}>
              <Button color="error" onClick={rejectButtonClick}>
                {editing ? <Close /> : <Delete />}
              </Button>
            </Tooltip>

            <Tooltip title={editing ? 'Accept' : 'Edit example'}>
              <Button color="primary" onClick={acceptButtonClick}>
                {editing ? <Check /> : <Edit />}
              </Button>
            </Tooltip>
          </ButtonGroup>
        </ListItemSecondaryAction>
      ) : null}
    </li>
  );
}

export default connector(Example);
