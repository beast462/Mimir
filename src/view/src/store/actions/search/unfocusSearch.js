import { SuggestionUses } from '~/const';
import ActionTypes from '@store/ActionTypes';

function unfocusSearch() {
  return dispatch =>
    dispatch({
      type: ActionTypes.SEARCH_SET_SUGGESTION_USE,
      payload: SuggestionUses.NONE,
    });
}

export default unfocusSearch;
