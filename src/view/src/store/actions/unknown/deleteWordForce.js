import ActionTypes from '@store/ActionTypes';

function deleteWordForce() {
  return (dispatch, getState) => {
    const {
      editor: { id },
      app: { viewingWord },
    } = getState();

    if (id === -1) return;

    if (window.bridge.deleteWord(id)) {
      dispatch({
        type: ActionTypes.APP_CHANGE_EDITING_WORD,
      });

      dispatch({
        type: ActionTypes.SEARCH_SET_SUGGESTIONS,
        payload: [],
      });

      if (id === viewingWord)
        dispatch({
          type: ActionTypes.APP_SELECT_WORD,
          payload: -1,
        });
    }

    dispatch({
      type: ActionTypes.APP_CLOSE_DIALOG,
    });
  };
}

export default deleteWordForce;
