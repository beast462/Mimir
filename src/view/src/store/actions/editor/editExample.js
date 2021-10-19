import ActionTypes from '@store/ActionTypes';

function editExample(id, eng, vie) {
  return async dispatch => {
    dispatch({
      type: ActionTypes.EDITOR_EDIT_EXAMPLE,
      payload: { id, english: eng, vietnamese: vie },
    });
  };
}

export default editExample;
