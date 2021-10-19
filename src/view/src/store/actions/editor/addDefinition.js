import ActionTypes from '@store/ActionTypes';

function addDefinition() {
  return dispatch => dispatch({ type: ActionTypes.EDITOR_ADD_DEFINITION });
}

export default addDefinition;
