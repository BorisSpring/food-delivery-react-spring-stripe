import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { loginUserApi, registerUserApi } from '../api/actions';

const initialState = {
  loading: false,
  error: '',
  errors: {},
};

export const registerUser = createAsyncThunk(
  'auth/registerUser',
  async (registerRequest, { rejectWithValue }) => {
    try {
      return await registerUserApi(registerRequest);
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const loginUser = createAsyncThunk(
  'auth/loginUser',
  async (loginRequest, { rejectWithValue }) => {
    try {
      return await loginUserApi(loginRequest);
    } catch (error) {
      return rejectWithValue(error.response.data?.msg);
    }
  }
);

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    resetAuthError: (state) => {
      state.loading = false;
      state.error = '';
    },
  },
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
        state.errors = action?.payload;
        state.error = action?.payload?.msg;
        state.loading = false;
      });
  },
});

export const { resetAuthError } = authSlice.actions;
export default authSlice.reducer;
