import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import './index.css';
import { BrowserRouter } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';
import { Provider } from 'react-redux';
import store from './redux/store.js';
import { getUser } from './redux/userReducer.js';
import { CartContextProvider } from './context/userCartContext';

const token = localStorage.getItem('jwt');

if (token) store.dispatch(getUser());

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <CartContextProvider>
      <BrowserRouter>
        <AnimatePresence>
          <Provider store={store}>
            <App />
          </Provider>
        </AnimatePresence>
      </BrowserRouter>
    </CartContextProvider>
  </React.StrictMode>
);
