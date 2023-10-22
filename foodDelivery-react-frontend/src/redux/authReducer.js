import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { loginUserApi, registerUserApi } from '../api/actions';

const initialState = {
  loading: false,
  error: '',
};

export const registerUser = createAsyncThunk(
  'auth/registerUser',
  async (registerRequest, { rejectWithValue }) => {
    try {
      const res = await registerUserApi(registerRequest);
      return res;
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

export const loginUser = createAsyncThunk(
  'auth/loginUser',
  async (loginRequest, { rejectWithValue }) => {
    try {
      const res = await loginUserApi(loginRequest);
      return res;
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

const authSlice = createSlice({
  name: 'auth',
  initialState,
  extraReducers: (builder) => {
    builder
      .addCase(loginUser.pending, (state) => {
        state.error = '';
        state.loading = true;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        if (action.payload?.auth === true)
          localStorage.setItem('jwt', action.payload.jwt);

        state.loading = false;
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.error = action.payload;
        state.loading = false;
      })
      .addCase(registerUser.pending, (state) => {
        state.error = '';
        state.loading = true;
      })
      .addCase(registerUser.fulfilled, (state, action) => {
        if (action.payload?.auth === true)
          localStorage.setItem('jwt', action.payload.jwt);
        state.loading = false;
      })
      .addCase(registerUser.rejected, (state, action) => {
        state.error = action.payload;
        state.loading = false;
      });
  },
});

export default authSlice.reducer;
