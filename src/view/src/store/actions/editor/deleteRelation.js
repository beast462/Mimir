import ActionTypes from '@store/ActionTypes';

function deleteRelation(defId, relId) {
  return dispatch =>
    dispatch({
      type: ActionTypes.EDITOR_DELETE_RELATION,
      payload: { defId, relId },
    });
}

export default deleteRelation;
