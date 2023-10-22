import { configureStore } from '@reduxjs/toolkit';
import userReducer from './userReducer';
import authReducer from './authReducer';
import alertReducer from './alertReducer';
import dashboardReducer from './dashboardReducer';

const store = configureStore({
  reducer: {
    user: userReducer,
    auth: authReducer,
    alert: alertReducer,
    dashboard: dashboardReducer,
  },
});

export default store;
