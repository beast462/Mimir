import ActionTypes from '@store/ActionTypes';

function deleteExample(exId) {
  return dispatch =>
    dispatch({
      type: ActionTypes.EDITOR_DELETE_EXAMPLE,
      payload: exId,
    });
}

export default deleteExample;
