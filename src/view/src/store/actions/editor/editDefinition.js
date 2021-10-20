import ActionTypes from '@store/ActionTypes';

function editDefinition(id, def, types) {
  return dispatch => {
    dispatch({
      type: ActionTypes.EDITOR_EDIT_DEFINITION,
      payload: { id, def, types },
    });
  };
}

export default editDefinition;
