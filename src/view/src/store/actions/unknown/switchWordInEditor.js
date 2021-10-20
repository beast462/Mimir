import ActionTypes from '@store/ActionTypes';

function switchWordInEditor() {
  return (dispatch, getState) => {
    const {
      app: { pendingSwitch },
    } = getState();

    if (pendingSwitch === -1) return;

    const word = window.bridge.getWord(pendingSwitch);

    if (word !== null) {
      dispatch({
        type: ActionTypes.EDITOR_PUT_WORD,
        payload: word,
      });

      dispatch({
        type: ActionTypes.APP_CLOSE_DIALOG,
      });
    }
  };
}

export default switchWordInEditor;
