import ActionTypes from '@store/ActionTypes';

function lockDialog() {
  return dispatch =>
    dispatch({
      type: ActionTypes.APP_LOCK_DIALOG,
    });
}

export default lockDialog;
