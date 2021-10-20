import {
  Button,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from '@mui/material';
import React from 'react';
import { connect } from 'react-redux';
import closeDialog from '@store/actions/app/closeDialog';
import deleteWordForce from '@store/actions/unknown/deleteWordForce';

const connector = connect(() => ({}), {
  closeDialog,
  deleteWordForce,
});

function RUSDialog(props) {
  return (
    <>
      <DialogTitle>Warning</DialogTitle>

      <DialogContent>
        <DialogContentText>
          This operation can not be undone. Do you want to proceed?
        </DialogContentText>
      </DialogContent>

      <DialogActions>
        <Button
          variant="contained"
          color="error"
          onClick={props.deleteWordForce}>
          accept
        </Button>

        <Button variant="text" color="inherit" onClick={props.closeDialog}>
          cancel
        </Button>
      </DialogActions>
    </>
  );
}

export default connector(RUSDialog);
