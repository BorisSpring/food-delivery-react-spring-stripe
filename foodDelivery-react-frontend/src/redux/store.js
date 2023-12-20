import { configureStore } from '@reduxjs/toolkit';
import userReducer from './userReducer';
import authReducer from './authReducer';
import alertReducer from './alertReducer';
import dashboardReducer from './dashboardReducer';
import productReducer from './productReducer';

const store = configureStore({
  reducer: {
    user: userReducer,
    auth: authReducer,
    alert: alertReducer,
    dashboard: dashboardReducer,
    products: productReducer,
  },
});

export default store;
