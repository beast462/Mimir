import ActionTypes from '@store/ActionTypes';
import { DialogContents } from '~/const';

function deleteWord() {
  return dispatch => {
    dispatch({
      type: ActionTypes.APP_OPEN_DIALOG,
      payload: DialogContents.ARE_YOU_SURE,
    });
  };
}

export default deleteWord;
