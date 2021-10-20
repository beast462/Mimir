import ActionTypes from '@store/ActionTypes';

function resetEditor() {
  return dispatch =>
    dispatch({
      type: ActionTypes.EDITOR_RESET,
    });
}

export default resetEditor;
