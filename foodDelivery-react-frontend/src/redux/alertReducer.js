import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  type: '',
  message: '',
};

const alertSlice = createSlice({
  name: 'alert',
  initialState,
  reducers: {
    warning: (state, action) => {
      state.type = 'warning';
      state.message = action.payload;
    },
    info: (state, action) => {
      state.type = 'info';
      state.message = action.payload;
    },
    danger: (state, action) => {
      state.type = 'danger';
      state.message = action.payload;
    },
    success: (state, action) => {
      state.type = 'success';
      state.message = action.payload;
    },
    setNull: (state, action) => {
      state.type = '';
      state.message = '';
    },
  },
});

export const { warning, info, danger, success, setNull } = alertSlice.actions;
export default alertSlice.reducer;
