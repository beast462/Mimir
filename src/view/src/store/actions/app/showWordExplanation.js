import ActionTypes from '@store/ActionTypes';
import { SuggestionUses } from '~/const';

function showWordExplanation(id) {
  return dispatch => {
    setTimeout(
      () =>
        dispatch({
          type: ActionTypes.SEARCH_SET_SUGGESTION_USE,
          payload: SuggestionUses.NONE,
        }),
      0,
    );

    dispatch({
      type: ActionTypes.APP_SELECT_WORD,
      payload: id,
    });
  };
}

export default showWordExplanation;
