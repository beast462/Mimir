import { DialogContents } from '~/const';
import ActionTypes from '@store/ActionTypes';
import openWordEditor from '@store/actions/app/openWordEditor';

function commitChange() {
  return (dispatch, getState) => {
    const { editor } = getState();
    const { bridge } = window;

    const word = bridge.absoluteSearch(editor.content.trim());

    if (word.length !== 0) {
      dispatch({
        type: ActionTypes.APP_PENDING_SWITCH_WORD,
        payload: word[0].id,
      });

      return dispatch({
        type: ActionTypes.APP_OPEN_DIALOG,
        payload: DialogContents.OVERRIDE,
      });
    }

    const id = bridge.addWord(editor);
    openWordEditor(id)(dispatch, getState);

    dispatch({
      type: ActionTypes.SEARCH_SET_SUGGESTIONS,
      payload: [],
    });
  };
}

export default commitChange;
