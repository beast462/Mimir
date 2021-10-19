import ActionTypes from '@store/ActionTypes';

function unlockDialog() {
  return dispatch =>
    dispatch({
      type: ActionTypes.APP_UNLOCK_DIALOG,
    });
}

export default unlockDialog;
