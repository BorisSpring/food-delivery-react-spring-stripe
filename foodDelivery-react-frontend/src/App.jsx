import React from 'react';
import { useSelector } from 'react-redux';
import { Routes, Route } from 'react-router-dom';

import {
  Alert,
  DBHome,
  DBItems,
  DBNewItem,
  DBOrders,
  DBUsers,
  Dashboard,
} from './components';
import HomePage from './pages/HomePage';
import Login from './pages/Login';

const App = () => {
  const alert = useSelector((store) => store.alert);

  return (
    <div className='w-screen min-h-screen h-auto flex flex-col items-center justify-center'>
      <Routes>
        <Route path='/*' element={<HomePage />} />
        <Route path='/login' element={<Login />} />
        <Route path='/dashboard' element={<Dashboard />}>
          <Route path='items' element={<DBItems />} />
          <Route path='users' element={<DBUsers />} />
          <Route path='addItem' element={<DBNewItem />} />
          <Route path='orders' element={<DBOrders />} />
          <Route path='home' element={<DBHome />} />
        </Route>
      </Routes>
      {alert.type && <Alert type={alert.type} message={alert.message} />}
    </div>
  );
};

export default App;
