import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getUserFromTokenApi } from '../api/actions';

const initialState = {
  totalPrice: 0,
  totalQuantity: 0,
  items: [],
  user: null,
  loading: false,
  error: '',
};

export const getUser = createAsyncThunk(
  'user/getUser',
  async (jwt, { rejectWithValue }) => {
    try {
      return await getUserFromTokenApi();
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

const userSlice = createSlice({
  initialState,
  name: 'user',
  extraReducers: (builder) => {
    builder
      .addCase(getUser.pending, (state) => {
        state.error = '';
        state.loading = true;
      })
      .addCase(getUser.fulfilled, (state, action) => {
        state.user = { ...action.payload };
        state.loading = false;
      })
      .addCase(getUser.rejected, (state, action) => {
        state.error = action.payload;
        state.loading = false;
      });
  },
  reducers: {
    addItem: (state, action) => {
      const product = state.items.find((item) => item.id === action.payload.id);

      if (product === undefined) {
        state.items = [...state.items, { ...action.payload, quantity: 1 }];
        state.totalQuantity = state.items.reduce(
          (acc, item) => item.quantity + acc,
          0
        );
        state.totalPrice = state.items.reduce(
          (acc, item) => item.price + acc,
          0
        );
      }
    },
    addItemQuantity: (state, action) => {
      const existingProductIndex = state.items.findIndex(
        (item) => item.id === action.payload.id
      );

      if (existingProductIndex >= 0) {
        state.items = state.items.map((item) =>
          item.id === action.payload.id
            ? { ...item, quantity: item.quantity + 1 }
            : { ...item }
        );
        state.totalQuantity += 1;
        state.totalPrice += action.payload.price;
      }
    },
  },
  decreseItemQuantity: (state, action) => {
    const product = state.items.find((item) => item.id === action.payload.id);

    if (product && product.quantity > 1) {
      state.items.map((item) =>
        item.id === action.payload.id
          ? { ...item, quantity: item.quantity - 1 }
          : { ...item }
      );
      state.totalQuantity -= 1;
      state.totalPrice -= action.payload.price;
    } else if (product && product.quantity === 1) {
      state.items.filter(({ id }) => id === action.payload.id);
      state.totalQuantity -= 1;
      state.totalPrice -= action.payload.price;
    }
  },
  clearCart: (state, action) => {
    state.items = [];
    state.totalPrice = 0;
    state.totalQuantity = 0;
  },
  removeItem: (state, action) => {
    const product = state.items.find((item) => item.id === action.payload.id);

    if (product) {
      state.totalPrice -= product.quantity * product.price;
      state.totalQuantity -= product.quantity;
      state.items.filter(({ id }) => id === action.payload.id);
    }
  },
  logout: (state) => {
    localStorage.removeItem('jwt');
    state = initialState;
  },
});

export const {
  removeItem,
  clearCart,
  decreseItemQuantity,
  addItemQuantity,
  addItem,
  logout,
} = userSlice.actions;
export default userSlice.reducer;
