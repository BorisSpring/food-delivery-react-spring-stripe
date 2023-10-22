import React from 'react';

import { NavLink } from 'react-router-dom';
import { isActiveStyles, isNotActiveStyles } from '../utils/styles';

const DBLeftSection = () => {
  return (
    <div className=' h-full min-h-screen pt-12 pb-2 flex flex-col bg-white shadow-md min-w-[210px]  w-[240px] gap-3'>
      <NavLink to='/' className='flex items-center justify-start px-6 gap-4'>
        <img src='public/logo.png' alt='Company Logo' className='w-10' />
        <p className='text-2xl font-semibold text-headingColor'>City</p>
      </NavLink>
      <hr />
      <ul className='flex flex-col gap-6 mt-5'>
        <li>
          <NavLink
            className={({ isActive }) =>
              isActive
                ? `${isActiveStyles} px-4 py-2 border-l-8 border-red-500`
                : isNotActiveStyles
            }
            to='home'
          >
            Home
          </NavLink>
        </li>
        <li>
          <NavLink
            className={({ isActive }) =>
              isActive
                ? `${isActiveStyles} px-4 py-2 border-l-8 border-red-500`
                : isNotActiveStyles
            }
            to='orders'
          >
            Orders
          </NavLink>
        </li>
        <li>
          <NavLink
            className={({ isActive }) =>
              isActive
                ? `${isActiveStyles} px-4 py-2 border-l-8 border-red-500`
                : isNotActiveStyles
            }
            to='items'
          >
            Items
          </NavLink>
        </li>
        <li>
          <NavLink
            className={({ isActive }) =>
              isActive
                ? `${isActiveStyles} px-4 py-2 border-l-8 border-red-500`
                : isNotActiveStyles
            }
            to='addItem'
          >
            Add New Item
          </NavLink>
        </li>
        <li>
          <NavLink
            className={({ isActive }) =>
              isActive
                ? `${isActiveStyles} px-4 py-2 border-l-8 border-red-500`
                : isNotActiveStyles
            }
            to='users'
          >
            Users
          </NavLink>
        </li>
      </ul>
      <div className='h-[225px] flex flex-col items-center justify-between  bg-red-400 rounded-md mx-2 py-4 px-2 mt-auto'>
        <div className='w-10 h-10 rounded-full bg-white flex items-center justify-center'>
          <p className='text-2xl font-[600] text-red-400'>?</p>
        </div>
        <p className='text-center text-xl font-[600] text-white'>Help Center</p>
        <p className='text-center text-white text-[13px]'>
          Having trouble in city. Please contact us for more questions
        </p>
        <button className='capitalize px-2 py-1 rounded-full bg-white text-red-500'>
          Get in touch
        </button>
      </div>
    </div>
  );
};

export default DBLeftSection;
