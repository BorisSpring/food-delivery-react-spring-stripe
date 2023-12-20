import React from 'react';
import DBHeader from './DBHeader';
import { Outlet } from 'react-router-dom';

const DBRightSection = () => {
  return (
    <div className='flex flex-col w-full py-12 flex-1 h-full '>
      <DBHeader />
      <Outlet />
    </div>
  );
};

export default DBRightSection;
