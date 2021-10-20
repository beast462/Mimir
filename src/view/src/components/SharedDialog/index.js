import { Dialog } from '@mui/material';
import React from 'react';
import { connect } from 'react-redux';
import closeDialog from '@store/actions/app/closeDialog';
import { makeStyles } from '@mui/styles';
import { DialogContents } from '~/const';
import AFEDDialog from './AFEDDialog';
import RUSDialog from './RUSDialog';
import FOVRDialog from './FOVRDialog';

const useStyles = makeStyles(theme => ({
  root: {
    zIndex: 1.1e9,
  },
}));

const connector = connect(
  state => ({
    dialogContent: state.app.dialogContent,
    allowClose: state.app.dialogAllowClose,
  }),
  { closeDialog },
);

function SharedDialog(props) {
  const classes = useStyles();

  const handleClose = () => {
    if (!props.allowClose) return;
    props.closeDialog();
  };

  return (
    <Dialog
      open={props.dialogContent !== DialogContents.NONE}
      onClose={handleClose}
      className={classes.root}>
      {(() => {
        switch (props.dialogContent) {
          case DialogContents.ASK_FOR_EXTERNAL_DATA:
            return <AFEDDialog />;

          case DialogContents.ARE_YOU_SURE:
            return <RUSDialog />;

          case DialogContents.OVERRIDE:
            return <FOVRDialog />;

          default:
            return null;
        }
      })()}
    </Dialog>
  );
}

export default connector(SharedDialog);
