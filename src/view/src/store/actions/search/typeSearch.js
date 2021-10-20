import ActionTypes from '@store/ActionTypes';

function calculateFitInInnerHeight() {
  return ((innerHeight * 0.6) / 32) | 0;
}

function typeSearch(content) {
  return dispatch => {
    if (content.length === 0)
      dispatch({
        type: ActionTypes.SEARCH_SET_SUGGESTIONS,
        payload: [],
      });
    else {
      try {
        const bridge = window.bridge;
        const itemCount = calculateFitInInnerHeight();
        const result = bridge.advanceSearch(content, itemCount);

        dispatch({
          type: ActionTypes.SEARCH_SET_SUGGESTIONS,
          payload: result,
        });
      } catch (e) {
        console.error(e.message);
      }
    }
  };
}

export default typeSearch;
