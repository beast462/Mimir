import { configureStore } from '@reduxjs/toolkit';
import thunk from 'redux-thunk';

import {
  reduce as searchReducer,
  initialState as searchInitialState,
} from './reducers/search';

import {
  reduce as appReducer,
  initialState as appInitialState,
} from './reducers/app';

import {
  reduce as editorReducer,
  initialState as editorInitialState,
} from './reducers/editor';

const store = configureStore({
  reducer: {
    search: searchReducer,
    app: appReducer,
    editor: editorReducer,
  },
  preloadedState: {
    search: searchInitialState,
    app: appInitialState,
    editor: editorInitialState,
  },
  middleware: getDefaultMiddleware => [...getDefaultMiddleware(), thunk],
  devTools: process.env.NODE_ENV !== 'production',
});

export default store;
