import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { createOrderApi, getUserFromTokenApi } from '../api/actions';

const initialState = {
  totalPrice: 0,
  totalQuantity: 0,
  items: [],
  user: null,
  loading: false,
  error: '',
};

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
      })
      .addCase(createOrder.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(createOrder.fulfilled, (state, action) => {
        state.items = [];
        state.totalPrice = 0;
        state.totalQuantity = 0;
        state.loading = false;
      })
      .addCase(createOrder.rejected, (state, action) => {
        state.loading = false;
        state.error = action?.payload;
      });
  },
  reducers: {
    addItem: (state, action) => {
      const product = state.items.find((item) => item.id === action.payload);
      if (product === undefined) {
        state.items = [...state.items, { ...action.payload, quantity: 1 }];
        state.totalQuantity = state.items.reduce(
          (acc, item) => item?.quantity + acc,
          0
        );
        state.totalPrice = state.items.reduce(
          (acc, item) => item?.price + acc,
          0
        );
      }
    },
    addItemQuantity: (state, action) => {
      const existingProduct = state.items.find(
        (item) => item.id === action.payload.id
      );

      if (existingProduct) {
        state.items = state.items.map((item) =>
          item.id === action.payload.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
        state.totalQuantity += 1;
        state.totalPrice += action.payload.price;
      }
    },
    decreaseItemQuantity: (state, action) => {
      const product = state.items.find((item) => item.id === action.payload.id);
      if (product) {
        if (product.quantity > 1) {
          state.items = state.items.map((item) =>
            item.id === action.payload.id
              ? { ...item, quantity: item.quantity - 1 }
              : item
          );
        } else {
          state.items = state.items.filter(
            ({ id }) => id !== action.payload.id
          );
        }
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
      const product = state.items.find((item) => item.id === action.payload);
      if (product) {
        state.totalPrice -= product.quantity * product.price;
        state.totalQuantity -= product.quantity;
        state.items.filter(({ id }) => id === action.payload);
      }
    },
    logout: (state) => {
      localStorage.removeItem('jwt');
      state = initialState;
    },
  },
});

export const {
  removeItem,
  clearCart,
  decreaseItemQuantity,
  addItemQuantity,
  addItem,
  logout,
} = userSlice.actions;

export const getCart = (store) => store.user.items;

export default userSlice.reducer;

export const getUser = createAsyncThunk(
  'user/getUser',
  async (_, { rejectWithValue }) => {
    try {
      return await getUserFromTokenApi();
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

export const createOrder = createAsyncThunk(
  '/products/findProductsByCategoryId',
  async (createOrderRequest, { rejectWithValue }) => {
    try {
      return await createOrderApi(createOrderRequest);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);
