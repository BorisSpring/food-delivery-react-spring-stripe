import React, { useState, useEffect } from 'react';
import { LoginInput } from '../components';
import { motion } from 'framer-motion';
import { buttonClick, fadeInOut } from '../animations/variants';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser, registerUser, resetAuthError } from '../redux/authReducer';
import { Navigate, useNavigate } from 'react-router-dom';
import { getUser } from '../redux/userReducer';
import { FaEnvelope, FaLock } from 'react-icons/fa';
import { FcGoogle } from 'react-icons/fc';
import GoogleSocialLogin from '../components/GoogleSocialLogin';
import { gapi } from 'gapi-script';

const initialState = {
  email: '',
  isSignUp: false,
  password: '',
  firstName: '',
  lastName: '',
  repeatedPassword: '',
};

const Login = () => {
  const [formState, setFormState] = useState(initialState);
  const dispatch = useDispatch();
  const { isLoading, error, errors } = useSelector((store) => store.auth);
  const user = useSelector((store) => store.user.user);
  const navigate = useNavigate();
  const handleInputChange = (fieldName, value) => {
    setFormState((prev) => ({ ...prev, [fieldName]: value }));
  };

  if (user) return <Navigate to='/' replace={true} />;

  return (
    <div className='w-screen h-screen relative overflow-hidden flex'>
      {isLoading && (
        <motion.div
          {...fadeInOut}
          className='backdrop-blur-md fixed z-50 w-full bg-cardOverlay flex items-center justify-center inset-0'
        >
          Loading...
        </motion.div>
      )}
      {/* background image */}
      <img
        src='public\assets\img\login.jpg'
        alt='Background'
        className='w-full h-full object-cover object-center absolute top-0 left-0 '
      />
      {/* content box */}
      <div className='flex flex-col items-center bg-light w-[80%] md:w-[500px] z-10 bg-[rgba(255,255,255,0.4)] backdrop-blur-[6px] px-4 py-12  gap-6'>
        {/* Top logo section */}
        <div className='flex items-center justify-start gap-4 w-full'>
          <img
            src='\assets\img\logo.png'
            alt='Compnay Logo'
            className='w-5 md:w-10'
          />
          <p className='text-headingColor font-semibold text-base md:text-xl'>
            City
          </p>
        </div>
        {/* welcome text */}
        <p className='md:text-3xl font-semibold text-headingColor'>
          Welcome Back
        </p>
        <p className='text-xl  text-gray-800 -mt-6'>
          {!formState.isSignUp ? 'Sign in' : 'Sign up'} with following
        </p>
        {/* login section */}
        <div className='w-full flex flex-col items-center justify-center gap-4 px-3 md:px-12 py-2'>
          <p className='text-lg  font-[500] text-black'>
            {error || errors?.email}
          </p>
          <LoginInput
            placeholder={'Email Here'}
            icon={<FaEnvelope className='text-lg  text-textColor' />}
            inputState={formState.email}
            inputStatefunc={(value) => handleInputChange('email', value)}
            type='email'
          />
          <p className='text-lg  font-[500] text-black'>{errors?.password}</p>
          <LoginInput
            placeholder={'Password Here'}
            icon={<FaLock className='text-xl text-textColor' />}
            inputState={formState.password}
            inputStatefunc={(value) => handleInputChange('password', value)}
            type='password'
          />
          {formState.isSignUp && (
            <>
              <p className='text-lg  font-[500] text-black'>
                {errors?.repeatedPassword}
              </p>
              <LoginInput
                placeholder={'Confirm password '}
                icon={<FaLock className='text-xl text-textColor' />}
                inputState={formState.repeatedPassword}
                inputStatefunc={(value) =>
                  handleInputChange('repeatedPassword', value)
                }
                type='password'
              />
              <p className='text-lg  font-[500] text-black'>
                {errors?.firstName}
              </p>
              <LoginInput
                placeholder={'First Name'}
                icon={<FaLock className='text-xl text-textColor' />}
                inputState={formState.firstName}
                inputStatefunc={(value) =>
                  handleInputChange('firstName', value)
                }
                type='text'
              />
              <p className='text-lg  font-[500] text-black'>
                {errors?.lastName}
              </p>
              <LoginInput
                placeholder={'Last Name'}
                icon={<FaLock className='text-xl text-textColor' />}
                inputState={formState.lastName}
                inputStatefunc={(value) => handleInputChange('lastName', value)}
                type='text'
              />
            </>
          )}
          {!formState.isSignUp ? (
            <p className='text-headingColor font-semibold'>
              Doesnt't have an account?{' '}
              <motion.button
                onClick={() =>
                  setFormState((prev) => ({
                    ...prev,
                    isSignUp: !formState.isSignUp,
                  }))
                }
                className='text-black underline cursor-pointer bg-transparent'
                {...buttonClick}
              >
                Create One
              </motion.button>{' '}
            </p>
          ) : (
            <p className='text-headingColor font-semibold'>
              Alerdy have an account?{' '}
              <motion.button
                onClick={() =>
                  setFormState((prev) => ({
                    ...prev,
                    isSignUp: !formState.isSignUp,
                  }))
                }
                className='text-red-400 underline cursor-pointer bg-transparent'
                {...buttonClick}
              >
                Sign up
              </motion.button>{' '}
            </p>
          )}

          <motion.button
            onClick={async () => {
              const { email, password, repeatedPassword, lastName, firstName } =
                formState;
              await dispatch(
                !formState.isSignUp
                  ? loginUser({ email, password })
                  : registerUser({
                      email,
                      password,
                      repeatedPassword,
                      lastName,
                      firstName,
                    })
              );

              if (localStorage.getItem('jwt')) {
                dispatch(getUser());
                navigate('/');
              } else {
                setTimeout(() => {
                  dispatch(resetAuthError());
                }, 10000);
              }
            }}
            {...buttonClick}
            className='w-full px-4 py-2 rounded-md bg-red-400 cursor-pointer text-white text-xl capitalize hover:bg-red-500 transition-all duration-150'
          >
            Sing {formState.isSignUp ? ' up' : ' in'}
          </motion.button>
        </div>
        <div className='flex items-center justify-between gap-16 '>
          <div className='w-24 h-[1px] rounded-md bg-white' />
          <p className='text-white'>or</p>
          <div className='w-24 h-[1px] rounded-md bg-white' />
        </div>
        <GoogleSocialLogin />
        {/* <motion.div
          {...buttonClick}
          className='flex items-center justify-center px-20 py-2 gap-5 bg-cardOverlay backdrop-blur-md cursor-pointer rounded-3xl'
        >
          <FcGoogle className='text-3xl' />
          <p className='capitalize text-base text-textColor'>
            Sign in with Google
          </p>
        </motion.div> */}
      </div>
    </div>
  );
};

export default Login;
