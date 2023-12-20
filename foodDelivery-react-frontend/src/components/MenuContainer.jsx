import React, { useState } from 'react';
import { motion } from 'framer-motion';
import RowContainer from './RowContainer';
import store from '../redux/store';
import {
  findCategories,
  findProductByCategoryId,
  getCategories,
  getSelectedProducts,
} from '../redux/productReducer';
import { useSelector } from 'react-redux';
import { MdFastfood } from 'react-icons/md';
import { useDispatch } from 'react-redux';

const MenuContainer = () => {
  const [selectedCategoryId, setSelectedCategoryId] = useState(1);
  const categories = useSelector(getCategories);
  const selectedProducts = useSelector(getSelectedProducts);
  const dispatch = useDispatch();
  return (
    <section className='w-full my-6' id='menu'>
      <div className='w-full flex flex-col items-center justify-center'>
        <p className='text-2xl font-semibold capitalize text-headingColor relative before:absolute before:rounded-lg before:content before:w-16 before:h-1 before:-bottom-2 before:left-0 before:bg-gradient-to-tr from-orange-400 to-orange-600 transition-all ease-in-out duration-100 mr-auto'>
          Our Hot Dishes
        </p>

        <div className='w-full flex items-center justify-start lg:justify-center gap-8 py-6 overflow-x-auto scrollbar'>
          {categories &&
            categories.map(({ id, name }) => (
              <motion.div
                whileTap={{ scale: 0.75 }}
                key={id}
                className={`group ${
                  selectedCategoryId === id ? 'bg-cartNumBg' : 'bg-card'
                } w-24 min-w-[94px] h-28 cursor-pointer rounded-lg drop-shadow-xl flex flex-col gap-3 items-center justify-center hover:bg-cartNumBg `}
                onClick={() => {
                  setSelectedCategoryId(id);
                  dispatch(findProductByCategoryId(id));
                }}
              >
                <div
                  className={`w-10 h-10 rounded-full shadow-lg ${
                    selectedCategoryId === id ? 'bg-white' : 'bg-cartNumBg'
                  } group-hover:bg-white flex items-center justify-center`}
                >
                  <MdFastfood
                    className={`${
                      selectedCategoryId === id
                        ? 'text-textColor'
                        : 'text-white'
                    } group-hover:text-textColor text-lg`}
                  />
                </div>
                <p
                  className={`text-sm ${
                    selectedCategoryId === id ? 'text-white' : 'text-textColor'
                  } group-hover:text-white`}
                >
                  {name}
                </p>
              </motion.div>
            ))}
        </div>

        <div className='w-full'>
          <RowContainer flag={false} data={selectedProducts} />
        </div>
      </div>
    </section>
  );
};
store.dispatch(findProductByCategoryId(1));
store.dispatch(findCategories());
export default MenuContainer;
