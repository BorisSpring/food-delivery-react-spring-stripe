import React from 'react';
import { motion } from 'framer-motion';
import { useDispatch } from 'react-redux';
import { addItemQuantity, decreaseItemQuantity } from '../redux/userReducer';
import { FaMinus } from 'react-icons/fa';
import { BsPlus } from 'react-icons/bs';

const CartItem = ({ image, title, quantity, id, price }) => {
  const dispatch = useDispatch();
  return (
    <div className='w-full p-1 px-2 rounded-lg bg-cartItem flex items-center gap-2'>
      <img
        src={`http://localhost:8080/api/products/image/${image}`}
        className='w-20 h-20 max-w-[60px] rounded-full object-contain'
        alt={image}
      />

      {/* name section */}
      <div className='flex flex-col gap-2'>
        <p className='text-base text-gray-50'>{title}</p>
        <p className='text-sm block text-gray-300 font-semibold'>
          $ {(price * quantity).toFixed(2)}
        </p>
      </div>

      {/* button section */}
      <div className='group flex items-center gap-2 ml-auto cursor-pointer'>
        <motion.div
          whileTap={{ scale: 0.75 }}
          onClick={() => dispatch(decreaseItemQuantity({ id, price }))}
        >
          <FaMinus className='text-gray-50 ' />
        </motion.div>

        <p className='w-5 h-5 rounded-sm bg-cartBg text-gray-50 flex items-center justify-center'>
          {quantity}
        </p>

        <motion.div
          whileTap={{ scale: 0.75 }}
          onClick={() => dispatch(addItemQuantity({ id, price }))}
        >
          <BsPlus className='text-gray-50' />
        </motion.div>
      </div>
    </div>
  );
};

export default CartItem;
