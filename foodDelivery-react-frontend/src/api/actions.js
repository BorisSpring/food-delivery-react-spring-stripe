import axios from 'axios';

const baseApi = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

baseApi.interceptors.request.use((config) => {
  const jwtToken = localStorage.getItem('jwt');
  if (jwtToken) {
    config.headers['Authorization'] = `Bearer ${jwtToken}`;
  }
  return config;
});

export async function registerUserApi(registerRequest) {
  const { data } = await baseApi.post('/auth/signup', registerRequest);
  return data;
}

export async function loginUserApi(loginRequest) {
  const { data } = await baseApi.post('/auth/signin', loginRequest);
  return data;
}

export async function getUserFromTokenApi() {
  const { data } = await baseApi.get('/auth', localStorage.getItem('jwt'));
  return data;
}

//categories

export async function findAllCategoriesApi() {
  const { data } = await baseApi.get('/api/categories');
  return data;
}

export async function addNewCategoryApi(categoryRequest) {
  const { data } = await baseApi.post('/api/categories', categoryRequest);
  return data;
}

export async function deleteCategoryByIdApi(categoryId) {
  const { data } = await baseApi.delete('/api/categories', {
    params: { categoryId: categoryId },
  });
  return data;
}

export async function findAllCategoriesProductByIdApi(categoryId) {
  const { data } = await baseApi.get(`/api/categories`, {
    params: { categoryId: categoryId },
  });
  return data;
}

//orders

export async function updateOrderApi(updateOrderRequest) {
  const { data } = await baseApi.put('/api/orders/update', updateOrderRequest);
  return data;
}

export async function findOrdersHandlerApi(page) {
  const { data } = await baseApi.get(
    `/api/orders?pageNumber=${Number(page + 1)}`
  );
  return data;
}

export async function createOrderApi(createOrderRequest) {
  const { data } = await baseApi.post(`/api/orders`, createOrderRequest);
  return data;
}

export async function cancelOrderApi(orderId) {
  const { data } = await baseApi.put(`/api/orders`, {
    params: { orderId: orderId },
  });
  return data;
}

export async function deleteOrderApi(orderId) {
  const { data } = await baseApi.delete(`/api/orders`, {
    params: { orderId: orderId },
  });
  return data;
}

export async function updateOrderStatusApi({ orderId, orderStatus }) {
  const { data } = await baseApi.put(`/api/orders/orderStatus`, orderStatus, {
    params: { orderId: orderId },
  });
  return data;
}

// products

export async function findImageApi(imageName) {
  const { data } = await baseApi.get(`/api/products/${imageName}`);
  return data;
}

export async function addProductApi(productRequest) {
  const { data } = await baseApi.post(`/api/products`, productRequest, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return data;
}

export async function findAllProductsApi({ pageIndex, pageSize }) {
  const { data } = await baseApi.get(`/api/products`, {
    params: {
      pageNumber: Number(pageIndex + 1),
      pageSize: pageSize,
    },
  });
  return data;
}

export async function deleteProductByIdApi(productId) {
  const { data } = await baseApi.delete(`/api/products`, {
    params: productId,
  });
  return data;
}

export async function disableProductByIdApi(productId) {
  const { data } = await baseApi.put(`/api/products/disable`, {
    params: productId,
  });
  return data;
}

export async function enableProductByIdApi(productId) {
  const { data } = await baseApi.put(`/api/products/enable`, {
    params: productId,
  });
  return data;
}

export async function findProductsByCategoryId(categoryId) {
  const { data } = await baseApi.get(`/api/products/category`, {
    params: { categoryId: categoryId },
  });
  return data;
}

//users

export async function findAllUsersApi({ pageNumber, pageSize }) {
  const { data } = await baseApi.get(`/api/users`, {
    params: {
      pageNumber: !!pageNumber > 1 ? pageNumber : 1,
      pageSize: !!pageSize > 4 ? pageSize : 5,
    },
  });
  return data;
}

export async function enableDisableUserApi(userId) {
  const { data } = await baseApi.put(`/api/users?userId=${userId}`);
  return data;
}

export async function deleteUserByIdApi(userId) {
  const { data } = await baseApi.delete(`/api/users`, {
    params: { userId: userId },
  });
  return data;
}

// stripe

export async function createCheckoutSession(StripePaymentRequest) {
  const { data } = await baseApi.post(
    `/api/v1/stripe/create-checkout-session`,
    StripePaymentRequest
  );
  return data;
}
