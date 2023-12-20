import React from 'react';
import { BsFillBellFill, BsToggles2 } from 'react-icons/bs';
import { MdLogout, MdSearch } from 'react-icons/md';
import { useDispatch, useSelector } from 'react-redux';
import { buttonClick } from '../animations/variants';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import { logout } from '../redux/userReducer';

const DBHeader = () => {
  const user = useSelector((store) => store.user.user);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  return (
    <div className='flex items-center justify-between px-4 md:px-10 w-full flex-col md:flex-row '>
      <div className='whitespace-nowrap mr-2'>
        <p className='text-headingColor font-medium'>Welcome to the city</p>
        <p className='text-textColor text-sm'>
          Hello {user?.firstName} {user?.lastName}
        </p>
      </div>
      <div className='flex flex-wrap justify-center md:justify-start gap-4 items-center w-full md:w-fit mt-5 md:mt-0'>
        <div className='flex items-center bg-white px-4 py-2 rounded-md gap-1 shadow-md'>
          <MdSearch className='text-gray-400 text-2xl' />
          <input
            type='text'
            placeholder='Search Here...'
            className='outline-none font-semibold text-textColor border-none  '
          />
          <BsToggles2 className='text-gray-400 text-xl' />
        </div>
        <div className='flex gap-2 items-center justify-center'>
          <motion.div
            {...buttonClick}
            className='w-9 h-9 rounded-md bg-white flex items-center justify-center cursor-pointer backdrop-blur-md shadow-md'
          >
            <BsFillBellFill className='text-gray-500' />
          </motion.div>
          <div className='w-9 h-9 rounded-md overflow-hidden bg-white shadow-md flex gap-2 items-center justify-center'>
            <motion.img
              whileHover={{ scale: 1.3 }}
              className='w-full h-full object-cover'
              src={`http://localhost:8080/api/products/image/${user.imageName}`}
            />
          </div>
          <motion.div
            {...buttonClick}
            className='w-9 h-9 bg-white flex items-center cursor-pointer justify-center rounded-md shadow-md'
            onClick={() => {
              dispatch(logout());
              navigate('/');
            }}
          >
            <MdLogout className='text-xl text-gray-400 ' />
          </motion.div>
        </div>
      </div>
    </div>
  );
};

export default DBHeader;
