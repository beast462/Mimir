import ActionTypes from '@store/ActionTypes';

function addExample(defId) {
  return dispatch =>
    dispatch({
      type: ActionTypes.EDITOR_ADD_EXAMPLE,
      payload: defId,
    });
}

export default addExample;
