import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { addProductApi } from '../api/actions';

const initialState = {
  loading: false,
  error: '',
  orders: [],
  items: [],
  users: [],
};

export const addNewProduct = createAsyncThunk(
  'dashboard/addProduct',
  async (productRequest, { rejectWithValue }) => {
    try {
      return await addProductApi(productRequest);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

const dashboardSlice = createSlice({
  name: 'dashboard',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(addNewProduct.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(addNewProduct.fulfilled, (state) => {
        state.loading = false;
      })
      .addCase(addNewProduct.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default dashboardSlice.reducer;
