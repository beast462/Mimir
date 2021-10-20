import ActionTypes from '@store/ActionTypes';

function addRelation(defId, word) {
  return dispatch =>
    dispatch({
      type: ActionTypes.EDITOR_ADD_RELATION,
      payload: { defId, word },
    });
}

export default addRelation;
