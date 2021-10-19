import ActionTypes from '@store/ActionTypes';
import { DialogContents } from '~/const';

const initialState = {
  editingWord: false,
  viewingWord: -1,
  pendingSwitch: -1,
  dialogContent: DialogContents.NONE,
  dialogAllowClose: true,
  wordTypeRefs: {},
};

function reduce(state = initialState, action) {
  switch (action.type) {
    case ActionTypes.APP_CHANGE_EDITING_WORD:
      return { ...state, editingWord: !state.editingWord };

    case ActionTypes.APP_CLOSE_DIALOG:
      return { ...state, dialogContent: DialogContents.NONE };

    case ActionTypes.APP_OPEN_DIALOG:
      return { ...state, dialogContent: action.payload };

    case ActionTypes.APP_LOCK_DIALOG:
      return { ...state, dialogAllowClose: false };

    case ActionTypes.APP_UNLOCK_DIALOG:
      return { ...state, dialogAllowClose: true };

    case ActionTypes.APP_SELECT_WORD:
      return { ...state, viewingWord: action.payload };

    case ActionTypes.APP_SET_WORD_TYPE_REFS:
      return { ...state, wordTypeRefs: action.payload };

    case ActionTypes.APP_PENDING_SWITCH_WORD:
      return { ...state, pendingSwitch: action.payload };

    default:
      return state;
  }
}

export { reduce, initialState };
