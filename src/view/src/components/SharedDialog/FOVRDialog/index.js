import {
  Button,
  DialogActions,
  DialogContent,
  DialogTitle,
} from '@mui/material';
import React from 'react';
import { connect } from 'react-redux';
import closeDialog from '@store/actions/app/closeDialog';
import commitChangeForce from '@store/actions/unknown/commitChangeForce';
import switchWordInEditor from '@store/actions/unknown/switchWordInEditor';

const connector = connect(() => ({}), {
  closeDialog,
  commitChangeForce,
  switchWordInEditor,
});

function FOVRDialog(props) {
  return (
    <>
      <DialogTitle>Warning</DialogTitle>

      <DialogContent>
        You committed a word that already existed. Do you want to override It?
      </DialogContent>

      <DialogActions>
        <Button variant="text" color="inherit" onClick={props.closeDialog}>
          cancel
        </Button>

        <Button
          variant="contained"
          color="info"
          onClick={props.switchWordInEditor}>
          show me that word
        </Button>

        <Button
          variant="contained"
          color="warning"
          onClick={props.commitChangeForce}>
          override
        </Button>
      </DialogActions>
    </>
  );
}

export default connector(FOVRDialog);
