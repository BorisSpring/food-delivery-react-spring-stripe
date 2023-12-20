import React, { useEffect, useState } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { isActiveStyles, isNotActiveStyles } from '../utils/styles';
import { motion } from 'framer-motion';
import { buttonClick, slideTop } from '../animations/variants';
import { useDispatch, useSelector } from 'react-redux';
import { MdLogout, MdShoppingCart } from 'react-icons/md';
import { logout } from '../redux/userReducer';
import { useCartContext } from '../context/userCartContext';

const Header = () => {
  const user = useSelector((store) => store.user.user);
  const [isMenu, setIsMenu] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { setIsCartOpen, isCartOpen } = useCartContext();
  const [isScrolled, setIsScrolled] = useState(false);
  const { items } = useSelector((store) => store.user);

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 50);
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <header
      className={`fixed  inset-x-0  transition-all duration-300 top-0 flex items-center justify-between px-12 md:px-20 py-2 md:py-3 z-50  ${
        isScrolled ? 'bg-white' : 'bg-primary'
      }`}
    >
      <NavLink to='/' className='flex items-center justify-center gap-4'>
        <img
          src='public\assets\img\logo.png'
          alt='Company Logo'
          className='w-10'
        />
        <p className='text-2xl font-semibold text-headingColor'>City</p>
      </NavLink>
      <nav className='flex items-center justify-between gap-7'>
        <ul className='hidden md:flex items-center justify-center gap-16'>
          <NavLink
            className={({ isActive }) =>
              isActive ? isActiveStyles : isNotActiveStyles
            }
            to='/'
          >
            Home
          </NavLink>
          <NavLink
            className={({ isActive }) =>
              isActive ? isActiveStyles : isNotActiveStyles
            }
            to='/menu'
          >
            Menu
          </NavLink>
          <NavLink
            className={({ isActive }) =>
              isActive ? isActiveStyles : isNotActiveStyles
            }
            to='/services'
          >
            Service
          </NavLink>
          <NavLink
            className={({ isActive }) =>
              isActive ? isActiveStyles : isNotActiveStyles
            }
            to='/aboutus'
          >
            About us
          </NavLink>
        </ul>

        <motion.div {...buttonClick} className='relative cursor-pointer'>
          <MdShoppingCart
            className='text-3xl text-textcolor'
            onClick={() => setIsCartOpen((prev) => !isCartOpen)}
          />
          {items?.length > 0 && (
            <div className='absolute w-5 h-5 rounded-full bg-red-500 text-white flex items-center justify-center -top-2 text-base -right-1'>
              {items?.length}
            </div>
          )}
        </motion.div>
        {user ? (
          <>
            <div
              className='relative cursor-pointer'
              onMouseEnter={() => setIsMenu(() => true)}
            >
              <div className='w-10 h-10 rounded-full shadow-md cursor-pointer overflow-hidden bg-green-300'>
                <motion.img
                  whileHover={{ scale: 1.15 }}
                  className='w-full h-full object-cover'
                  src={`http://localhost:8080/api/products/image/${user.imageName}`}
                  referrerPolicy='no-referrer'
                />
              </div>
              {isMenu && (
                <motion.div
                  {...slideTop}
                  onMouseLeave={() => setIsMenu(() => false)}
                  className='px-6 py-4 bg-cardOverlay rounded-md shadow-md absolute backdrop-blur-md top-5 right-0 flex flex-col gap-3 '
                >
                  <NavLink
                    className='hover:text-red-500 text-base text-textColor '
                    to='/dashboard'
                  >
                    Dashboard
                  </NavLink>
                  <NavLink
                    className='hover:text-red-500 text-base text-textColor '
                    to='/profile'
                  >
                    Profile
                  </NavLink>
                  <NavLink
                    className='hover:text-red-500 text-base text-textColor '
                    to='/orders'
                  >
                    Orders
                  </NavLink>
                  <hr />
                  <motion.div
                    onClick={() => {
                      navigate('/login');
                      dispatch(logout());
                    }}
                    {...buttonClick}
                    className='group flex items-center justify-center px-4 rounded-md shadow-md bg-gray-200 hover:bg-gray-100 transition-all duration-300 gap-2 py-2'
                  >
                    <MdLogout className='text-2xl text-textColor group-hover:text-headingColor' />
                    <p className='text-sm whitespace-nowrap text-textColor group-hover:text-headingColor'>
                      Sign out
                    </p>
                  </motion.div>
                </motion.div>
              )}
            </div>
          </>
        ) : (
          <NavLink to='/login'>
            <motion.button
              {...buttonClick}
              className='px-4 py-1  rounded-md shadow-md bg-cardOverlay border-red-300 border-2 '
            >
              Login
            </motion.button>
          </NavLink>
        )}
      </nav>
    </header>
  );
};

export default Header;
