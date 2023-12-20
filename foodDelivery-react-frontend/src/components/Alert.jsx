import React from 'react';
import { motion } from 'framer-motion';
import { fadeInOut } from '../animations/variants';
import {
  BsExclamationTriangle,
  BsExclamationTriangleFill,
} from 'react-icons/bs';
import { Check } from '@mui/icons-material';

const Alert = ({ type, message }) => {
  if (type === 'success')
    return (
      <motion.div
        {...fadeInOut}
        className='fixed z-50 backdrop-blur-sm top-32 left-16 rounded-md px-8 py-2 bg-emerald-200 flex items-center gap-4'
      >
        <Check className='text-xl text-emerald-700' />
        <p className='text-xl text-emerald-700'>{message}</p>
      </motion.div>
    );
  if (type === 'warning')
    return (
      <motion.div
        {...fadeInOut}
        className='fixed z-50 backdrop-blur-sm top-32 left-16 rounded-md px-8 py-2 bg-orange-200 flex items-center gap-4'
      >
        <BsExclamationTriangle className='text-xl text-orange-700' />
        <p className='text-xl text-orange-700'>{message}</p>
      </motion.div>
    );
  if (type === 'danger')
    return (
      <motion.div
        {...fadeInOut}
        className='fixed z-50 backdrop-blur-sm top-32 left-16 rounded-md px-8 py-2 bg-red-200 flex items-center gap-4'
      >
        <BsExclamationTriangleFill className='text-xl text-red-700' />
        <p className='text-xl text-red-700'>{message}</p>
      </motion.div>
    );
  if (type === 'info')
    return (
      <motion.div
        {...fadeInOut}
        className='fixed z-50 backdrop-blur-sm top-32 left-16 rounded-md px-8 py-2 bg-yellow-200 flex items-center gap-4'
      >
        <BsExclamationTriangleFill className='text-xl text-yellow-700' />
        <p className='text-xl text-yellow-700'>{message}</p>
      </motion.div>
    );
};

export default Alert;
