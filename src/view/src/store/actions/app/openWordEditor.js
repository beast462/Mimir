import ActionTypes from '@store/ActionTypes';

function openWordEditor(id) {
  return (dispatch, getState) => {
    const {
      app: { editingWord },
    } = getState();

    if (!editingWord) {
      if (typeof id === 'number') {
        const word = window.bridge.getWord(id);

        dispatch({
          type: ActionTypes.EDITOR_PUT_WORD,
          payload: word,
        });
      }

      dispatch({
        type: ActionTypes.APP_CHANGE_EDITING_WORD,
      });
    }
  };
}

export default openWordEditor;
