import { SuggestionUses } from '~/const';
import ActionTypes from '@store/ActionTypes';

const initialState = {
  suggestions: [],
  suggestionUse: SuggestionUses.NONE,
};

function reduce(state = initialState, action) {
  switch (action.type) {
    case ActionTypes.SEARCH_SET_SUGGESTIONS:
      return { ...state, suggestions: action.payload ?? [] };

    case ActionTypes.SEARCH_SET_SUGGESTION_USE:
      return { ...state, suggestionUse: action.payload ?? SuggestionUses.NONE };

    default:
      return state;
  }
}

export { reduce, initialState };
