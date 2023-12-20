import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import {
  addNewCategoryApi,
  addProductApi,
  cancelOrderApi,
  deleteCategoryByIdApi,
  deleteOrderApi,
  deleteProductByIdApi,
  deleteUserByIdApi,
  disableProductByIdApi,
  enableDisableUserApi,
  enableProductByIdApi,
  findAllProductsApi,
  findAllUsersApi,
  findOrdersHandlerApi,
  updateOrderApi,
  updateOrderStatusApi,
} from '../api/actions';

const initialState = {
  loading: false,
  error: '',
  orders: [],
  items: [],
  users: [],
};

const dashboardSlice = createSlice({
  name: 'dashboard',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(deleteUser.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(deleteUser.fulfilled, (state, action) => {
        state.users = {
          ...state.users,
          content: state.users.content.filter(
            (user) => user.id !== action.meta.arg
          ),
        };
        state.loading = false;
      })
      .addCase(deleteUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(findAllUsers.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(findAllUsers.fulfilled, (state, action) => {
        state.users = action.payload;
        state.loading = false;
      })
      .addCase(findAllUsers.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(enableDisableUser.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(enableDisableUser.fulfilled, (state, action) => {
        state.users = {
          ...state.users,
          content: state.users.content.map((user) =>
            user.id === action.meta.arg
              ? { ...user, enabled: !user.enabled }
              : user
          ),
        };
        state.loading = false;
      })
      .addCase(enableDisableUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

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
      })

      .addCase(findAllProducts.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(findAllProducts.fulfilled, (state, action) => {
        state.items = action.payload;
        state.loading = false;
      })
      .addCase(findAllProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(deleteProduct.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.items = state.items.filter((item) => item !== action.meta);
        state.loading = false;
      })
      .addCase(deleteProduct.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(disableProduct.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(disableProduct.fulfilled, (state, action) => {
        state.items = state.items.map((item) =>
          item.id === action.meta ? { ...item, status: false } : item
        );
        state.loading = false;
      })
      .addCase(disableProduct.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(enableProduct.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(enableProduct.fulfilled, (state, action) => {
        state.items = state.items.map((item) =>
          item.id === action.meta ? { ...item, status: true } : item
        );
        state.loading = false;
      })
      .addCase(enableProduct.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(addNewCategory.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(addNewCategory.fulfilled, (state) => {
        state.loading = false;
      })
      .addCase(addNewCategory.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(findOrders.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(findOrders.fulfilled, (state, action) => {
        state.orders = action.payload;
        state.loading = false;
      })
      .addCase(findOrders.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(updateOrder.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(updateOrder.fulfilled, (state, action) => {
        state.orders = state.orders.map((order) =>
          order.id === action.payload.id ? action.payload : order
        );
        state.loading = false;
      })
      .addCase(updateOrder.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(cancelOrder.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(cancelOrder.fulfilled, (state, action) => {
        state.orders = state.orders.map((order) =>
          order.id === action.meta ? { ...order, status: 'CANCELED' } : order
        );
        state.loading = false;
      })
      .addCase(cancelOrder.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(deleteOrder.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(deleteOrder.fulfilled, (state, action) => {
        state.orders = state.orders.filter(
          (order) => order.id !== action.meta.arg
        );
        state.loading = false;
      })
      .addCase(deleteOrder.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      .addCase(updateOrderStatus.pending, (state) => {
        state.loading = true;
        state.error = '';
      })
      .addCase(updateOrderStatus.fulfilled, (state, action) => {
        const { orderId, orderStatus } = action.meta.arg;
        state.orders = state.orders.map((order) =>
          order.id === orderId ? { ...order, orderStatus } : order
        );
        state.loading = false;
      })
      .addCase(updateOrderStatus.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default dashboardSlice.reducer;

// users

export const findAllUsers = createAsyncThunk(
  '/dashboard/findUsers',
  async (paginationRequest, rejectWithValue) => {
    try {
      return await findAllUsersApi(paginationRequest);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

export const enableDisableUser = createAsyncThunk(
  '/dashboard/enableDisableUser',
  async (userId, rejectWithValue) => {
    try {
      return await enableDisableUserApi(userId);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

export const deleteUser = createAsyncThunk(
  '/dashboard/deleteUser',
  async (userId, rejectWithValue) => {
    try {
      return await deleteUserByIdApi(userId);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

//products

export const findAllProducts = createAsyncThunk(
  '/dashboard/findProducts',
  async (pagination, rejectWithValue) => {
    try {
      return await findAllProductsApi(pagination);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

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

export const deleteProduct = createAsyncThunk(
  '/dashboard/deleteProduct',
  async (productId, { rejectWithValue }) => {
    try {
      return await deleteProductByIdApi(productId);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

export const disableProduct = createAsyncThunk(
  '/dashboard/disableProduct',
  async (productId, { rejectWithValue }) => {
    try {
      return await disableProductByIdApi(productId);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

export const enableProduct = createAsyncThunk(
  '/dashboard/enableProduct',
  async (productId, { rejectWithValue }) => {
    try {
      return await enableProductByIdApi(productId);
    } catch (error) {
      return rejectWithValue(error.response.data.msg);
    }
  }
);

// categories

export const addNewCategory = createAsyncThunk(
  '/dashboard/addNewCategory',
  async (createCategoryRequest, { rejectWithValue }) => {
    try {
      return await addNewCategoryApi(createCategoryRequest);
    } catch (error) {
      return rejectWithValue(error?.response.data.msg);
    }
  }
);

export const deleteCategoryById = createAsyncThunk(
  '/dashboard/deleteCategoryById',
  async (categoryId, { rejectWithValue }) => {
    try {
      return await deleteCategoryByIdApi(categoryId);
    } catch (error) {
      return rejectWithValue(error?.response.data.msg);
    }
  }
);

// orders

export const findOrders = createAsyncThunk(
  '/dashboard/findOrders',
  async (page, { rejectWithValue }) => {
    try {
      return await findOrdersHandlerApi(page);
    } catch (error) {
      return rejectWithValue(error?.response.data.msg);
    }
  }
);

export const updateOrder = createAsyncThunk(
  '/dashboard/updateOrder',
  async (updateOrderRequest, { rejectWithValue }) => {
    try {
      return await updateOrderApi(updateOrderRequest);
    } catch (error) {
      return rejectWithValue(error?.response.data.msg);
    }
  }
);

export const cancelOrder = createAsyncThunk(
  '/dashboard/cancelOrder',
  async (orderId, { rejectWithValue }) => {
    try {
      return await cancelOrderApi(orderId);
    } catch (error) {
      return rejectWithValue(error?.response.data.msg);
    }
  }
);

export const deleteOrder = createAsyncThunk(
  '/dashboard/deleteOrder',
  async (orderId, { rejectWithValue }) => {
    try {
      return await deleteOrderApi(orderId);
    } catch (error) {
      return rejectWithValue(error?.response.data.msg);
    }
  }
);

export const updateOrderStatus = createAsyncThunk(
  '/dashboard/updateOrderStatus',
  async ({ orderId, orderStatus }, { rejectWithValue }) => {
    try {
      return await updateOrderStatusApi({ orderId, orderStatus });
    } catch (error) {
      return rejectWithValue(error?.response.data.msg);
    }
  }
);
