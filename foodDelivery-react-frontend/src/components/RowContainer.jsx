import React, { useEffect, useRef } from 'react';
import { motion } from 'framer-motion';
import { MdShoppingBasket } from 'react-icons/md';
import { useDispatch } from 'react-redux';
import { addItem } from '../redux/userReducer';

const RowContainer = ({ flag = false, data, scrollValue = 0 }) => {
  const rowContainer = useRef();
  const dispatch = useDispatch();

  useEffect(() => {
    rowContainer.current.scrollLeft += scrollValue;
  }, [scrollValue]);

  return (
    <div
      ref={rowContainer}
      className={`w-full  flex items-center gap-3 scroll-smooth ${
        flag
          ? 'overflow-x-scroll-auto scrollbar-none'
          : 'overflow-x-hidden flex-wrap justify-center'
      }`}
    >
      {data && data.length > 0 ? (
        data.map(({ id, image, name, calories, price }) => (
          <div
            key={id}
            className='overflow-y-hidden w-275 h-[175px] min-w-[275px] md:w-300 md:min-w-[300px] overflow-x-hidden  bg-cardOverlay rounded-lg py-2 px-4  my-12 backdrop-blur-lg hover:drop-shadow-lg flex flex-col items-center justify-evenly relative'
          >
            <div className='w-full flex items-center justify-between'>
              <motion.div
                className='w-40 h-40 -mt-8 drop-shadow-2xl'
                whileHover={{ scale: 1.2 }}
              >
                <img
                  src={`http://localhost:8080/api/products/image/${image}`}
                  alt={image}
                  className='w-full h-full object-contain'
                />
              </motion.div>
              <motion.div
                whileTap={{ scale: 0.75 }}
                className='w-8 h-8 rounded-full bg-red-600 flex items-center justify-center cursor-pointer hover:shadow-md -mt-8'
                onClick={() =>
                  dispatch(addItem({ id, image, name, calories, price }))
                }
              >
                <MdShoppingBasket className='text-white' />
              </motion.div>
            </div>

            <div className='w-full flex flex-col items-end justify-end -mt-8'>
              <p className='text-textColor font-semibold text-base md:text-lg'>
                {name}
              </p>
              <p className='mt-1 text-sm text-gray-500'>{calories} Calories</p>
              <div className='flex items-center gap-8'>
                <p className='text-lg text-headingColor font-semibold'>
                  <span className='text-sm text-red-500'>$</span> {price}
                </p>
              </div>
            </div>
          </div>
        ))
      ) : (
        <div className='w-full flex flex-col items-center justify-center'>
          <img src='posle dodaj' className='h-340' alt='' />
          <p className='text-xl text-headingColor font-semibold my-2'>
            Items Not Available
          </p>
        </div>
      )}
    </div>
  );
};

export default RowContainer;
