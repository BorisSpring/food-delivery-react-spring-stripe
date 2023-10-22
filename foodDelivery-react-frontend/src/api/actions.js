import axios from 'axios';

const baseApi = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  },
});

export async function registerUserApi(registerRequest) {
  try {
    const { data } = await baseApi.post('/auth/signup', registerRequest);
    return data;
  } catch (error) {
    console.error(error.response.data.msg);
  }
}

export async function loginUserApi(loginRequest) {
  try {
    const { data } = await baseApi.post('/auth/signin', loginRequest);
    return data;
  } catch (error) {
    console.error(error.response.data.msg);
  }
}

export async function getUserFromTokenApi() {
  try {
    const { data } = await baseApi.post('/auth', localStorage.getItem('jwt'));
    return data;
  } catch (error) {
    console.error(error.response.data.msg);
  }
}

export async function addProductApi(productRequest) {
  try {
    const { data } = await baseApi.post('/api/products', productRequest);
    return data;
  } catch (error) {
    console.error(error.response.data.msg);
  }
}
