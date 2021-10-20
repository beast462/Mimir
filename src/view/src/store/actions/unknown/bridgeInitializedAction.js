import ActionTypes from '@store/ActionTypes';
import { DialogContents } from '~/const';

function bridgeInitializedAction() {
  return dispatch => {
    const exDataLoadState = window.bridge.getAgreement('external_data');

    if (exDataLoadState === 0) {
      setTimeout(() => {
        dispatch({
          type: ActionTypes.APP_OPEN_DIALOG,
          payload: DialogContents.ASK_FOR_EXTERNAL_DATA,
        });
      }, 500);
    }

    const wordTypeRefs = window.bridge.getWordTypeReferences();
    dispatch({
      type: ActionTypes.APP_SET_WORD_TYPE_REFS,
      payload: wordTypeRefs,
    });
  };
}

export default bridgeInitializedAction;
