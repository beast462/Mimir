import ActionTypes from '@store/ActionTypes';

function editWord(field, value) {
  return dispatch =>
    dispatch({
      type: ActionTypes.EDITOR_EDIT_FIELD,
      payload: {
        field,
        value,
      },
    });
}

export default editWord;
