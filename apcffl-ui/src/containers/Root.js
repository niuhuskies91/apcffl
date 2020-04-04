import React from 'react';
import { hot } from 'react-hot-loader/root'

import App from 'Components/App';
import 'Styles/Base.scss';

export class Root extends React.PureComponent {
  render () {
    return (
      <App />
    );
  }
}

export default hot(Root);
