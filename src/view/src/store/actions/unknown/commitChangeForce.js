import ActionTypes from '@store/ActionTypes';

function commitChangeForce() {
  return (dispatch, getState) => {
    const {
      editor,
      app: { viewingWord },
    } = getState();
    const { bridge } = window;

    const oldId = editor.id;

    const newId = bridge.editWord(editor);
    const word = bridge.getWord(newId);

    dispatch({
      type: ActionTypes.EDITOR_PUT_WORD,
      payload: word,
    });

    if (viewingWord === oldId)
      dispatch({
        type: ActionTypes.APP_SELECT_WORD,
        payload: newId,
      });

    dispatch({
      type: ActionTypes.APP_CLOSE_DIALOG,
    });

    dispatch({
      type: ActionTypes.SEARCH_SET_SUGGESTIONS,
      payload: [],
    });
  };
}

export default commitChangeForce;
