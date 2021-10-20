import ActionTypes from '@store/ActionTypes';

function openDialog(dialogContent) {
  return dispatch => {
    dispatch({
      type: ActionTypes.APP_OPEN_DIALOG,
      payload: dialogContent,
    });
  };
}

export default openDialog;
