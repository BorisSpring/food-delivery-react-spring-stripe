import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { findAllCategoriesApi, findProductsByCategoryId } from '../api/actions';

const initialState = {
  loading: false,
  categories: [],
  fruits: [],
  selectedCategoryProducts: [],
  error: '',
};

const productSlice = createSlice({
  name: 'products',
  initialState,
  extraReducers: (builder) => {
    builder
      .addCase(findProductByCategoryId.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(findProductByCategoryId.fulfilled, (state, action) => {
        state.selectedCategoryProducts = action?.payload;
        state.loading = false;
      })
      .addCase(findProductByCategoryId.rejected, (state, action) => {
        state.loading = false;
        state.error = action?.payload;
      })
      .addCase(findFruits.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(findFruits.fulfilled, (state, action) => {
        state.fruits = action?.payload;
        state.loading = false;
      })
      .addCase(findFruits.rejected, (state, action) => {
        state.loading = false;
        state.error = action?.payload;
      })
      .addCase(findCategories.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(findCategories.fulfilled, (state, action) => {
        state.categories = action.payload;
        state.loading = false;
      })
      .addCase(findCategories.rejected, (state, action) => {
        state.loading = false;
        state.error = action?.payload;
      });
  },
});

export const getFruits = (state) => state.products.fruits;
export const getCategories = (state) => state.products.categories;
export const getSelectedProducts = (state) =>
  state.products.selectedCategoryProducts;
export default productSlice.reducer;

export const findFruits = createAsyncThunk(
  '/products/findFruits',
  async (_, { rejectWithValue }) => {
    try {
      return await findProductsByCategoryId(2);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

export const findCategories = createAsyncThunk(
  '/products/categories',
  async (_, { rejectWithValue }) => {
    try {
      return await findAllCategoriesApi();
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const findProductByCategoryId = createAsyncThunk(
  '/products/findProductsByCategoryId',
  async (categoryId, { rejectWithValue }) => {
    try {
      return await findProductsByCategoryId(categoryId);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);
