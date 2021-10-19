import {
  Button,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from '@mui/material';
import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import closeDialog from '@store/actions/app/closeDialog';
import lockDialog from '@store/actions/app/lockDialog';
import unlockDialog from '@store/actions/app/unlockDialog';
import fetchExternalData from '@store/actions/unknown/fetchExternalData';
import denyDataRecommendation from '@store/actions/unknown/denyDataRecommendation';
import AFEDBody from './AFEDBody';

const connector = connect(() => ({}), {
  closeDialog,
  fetchExternalData,
  denyDataRecommendation,
  lockDialog,
  unlockDialog,
});

const staticRefs = {};

function AFEDDialog(props) {
  const [scene, setScene] = useState(0);
  staticRefs.setScene = setScene;
  staticRefs.unlockDialog = props.unlockDialog;
  staticRefs.closeDialog = props.closeDialog;

  const handleReject = () => {
    props.denyDataRecommendation();
    props.closeDialog();
    props.unlockDialog();
  };

  const handleAccept = () => {
    setScene(1);
    props.fetchExternalData().then(() => {
      staticRefs.setScene(2);

      setTimeout(() => {
        staticRefs.closeDialog();
        staticRefs.unlockDialog();
      }, 2000);
    });
  };

  useEffect(() => {
    props.lockDialog();
  }, []);

  return (
    <>
      {scene === 0 ? <DialogTitle>No data yet</DialogTitle> : null}
      <DialogContent>
        <AFEDBody scene={scene} />
      </DialogContent>
      {scene === 0 ? (
        <DialogActions>
          <Button variant="text" color="inherit" onClick={handleReject}>
            deny
          </Button>

          <Button
            variant="text"
            color="info"
            onClick={() => [props.closeDialog(), props.unlockDialog()]}>
            remind later
          </Button>

          <Button variant="text" color="info" onClick={handleAccept}>
            accept
          </Button>
        </DialogActions>
      ) : null}
    </>
  );
}

export default connector(AFEDDialog);
