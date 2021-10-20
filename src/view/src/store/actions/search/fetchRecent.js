import ActionTypes from '@store/ActionTypes';

function fetchRecent() {
  return (dispatch, getState) => {
    const {
      search: { suggestions },
    } = getState();

    if (suggestions.length !== 0) return;

    dispatch({
      type: ActionTypes.SEARCH_SET_SUGGESTIONS,
      payload: [],
    });
  };
}

export default fetchRecent;
