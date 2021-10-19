import ActionTypes from '@store/ActionTypes';

function closeWordEditor(id) {
  return (dispatch, getState) => {
    const {
      app: { editingWord },
    } = getState();

    if (editingWord)
      dispatch({
        type: ActionTypes.APP_CHANGE_EDITING_WORD,
      });
  };
}

export default closeWordEditor;
