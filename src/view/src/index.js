import React from 'react';
import ReactDOM from 'react-dom';
import 'typeface-roboto';
import './mock-bridge';
import App from './App';

import './style/index.css';
import './style/scrollbar.css';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root'),
);
