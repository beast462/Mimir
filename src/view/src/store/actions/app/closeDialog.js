import ActionTypes from '@store/ActionTypes';
import { DialogContents } from '~/const';

function closeDialog() {
  return (dispatch, getState) => {
    const {
      app: { dialogContent },
    } = getState();

    if (dialogContent !== DialogContents.NONE)
      return dispatch({
        type: ActionTypes.APP_CLOSE_DIALOG,
      });
  };
}

export default closeDialog;
