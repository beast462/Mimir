import ActionTypes from '@store/ActionTypes';

function deleteDefinition(id) {
  return dispatch =>
    dispatch({
      type: ActionTypes.EDITOR_DELETE_DEFINITION,
      payload: id,
    });
}

export default deleteDefinition;
