import { SuggestionUses } from '~/const';
import ActionTypes from '@store/ActionTypes';

function focusSearch() {
  return dispatch =>
    dispatch({
      type: ActionTypes.SEARCH_SET_SUGGESTION_USE,
      payload: SuggestionUses.SEARCH,
    });
}

export default focusSearch;
