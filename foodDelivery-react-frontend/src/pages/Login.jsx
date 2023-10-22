import React, { useState } from 'react';
import { LoginBg, Logo } from '../assets';
import { LoginInput } from '../components';
import { FaEnvelope, FaLock, FcGoogle } from '../assets/icons/index';
import { motion } from 'framer-motion';
import { buttonClick, fadeInOut } from '../animations/variants';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser, registerUser } from '../redux/authReducer';
import { Navigate } from 'react-router-dom';
import { danger, success, warning } from '../redux/alertReducer';
import { getUser } from '../redux/userReducer';

const Login = () => {
  const [email, setEmail] = useState('');
  const [isSignUp, setIsSignUp] = useState(false);
  const [password, setPassword] = useState('');
  const [repeatedPassword, setRepeatedPassword] = useState('');
  const dispatch = useDispatch();
  const isLoading = useSelector((store) => store.auth.loading);
  const user = useSelector((store) => store.user.user);

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
        src={LoginBg}
        alt='Background '
        className='w-full h-full object-cover object-center absolute top-0 left-0 '
      />
      {/* content box */}
      <div className='flex flex-col items-center bg-light w-[80%] md:w-[500px] z-10 bg-[rgba(255,255,255,0.4)] backdrop-blur-[6px] px-4 py-12  gap-6'>
        {/* Top logo section */}
        <div className='flex items-center justify-start gap-4 w-full'>
          <img src={Logo} alt='Compnay Logo' className='w-5 md:w-10' />
          <p className='text-headingColor font-semibold text-base md:text-xl'>
            City
          </p>
        </div>
        {/* welcome text */}
        <p className='md:text-3xl font-semibold text-headingColor'>
          Welcome Back
        </p>
        <p className='text-xl  text-gray-800 -mt-6'>
          {!isSignUp ? 'Sign in' : 'Sign up'} with following
        </p>

        {/* login section */}
        <div className='w-full flex flex-col items-center justify-center gap-6 px-3 md:px-12 py-2'>
          <LoginInput
            placeholder={'Email Here'}
            icon={<FaEnvelope className='text-2xl text-textColor' />}
            inputState={email}
            inputStatefunc={setEmail}
            type='email'
            isSignUp={isSignUp}
          />
          <LoginInput
            placeholder={'Password Here'}
            icon={<FaLock className='text-xl text-textColor' />}
            inputState={password}
            inputStatefunc={setPassword}
            type='password'
            isSignUp={isSignUp}
          />
          {isSignUp && (
            <LoginInput
              placeholder={'Confirm password '}
              icon={<FaLock className='text-xl text-textColor' />}
              inputState={repeatedPassword}
              inputStatefunc={setRepeatedPassword}
              type='password'
              isSignUp={isSignUp}
            />
          )}
          {!isSignUp ? (
            <p className='text-headingColor font-semibold'>
              Doesnt't have an account?{' '}
              <motion.button
                onClick={() => setIsSignUp((prev) => !prev)}
                className='text-red-400 underline cursor-pointer bg-transparent'
                {...buttonClick}
              >
                Create One
              </motion.button>{' '}
            </p>
          ) : (
            <p className='text-headingColor font-semibold'>
              Alerdy have an account?{' '}
              <motion.button
                onClick={() => setIsSignUp((prev) => !prev)}
                className='text-red-400 underline cursor-pointer bg-transparent'
                {...buttonClick}
              >
                Sign up
              </motion.button>{' '}
            </p>
          )}

          <motion.button
            onClick={async () => {
              if (password?.trim().length < 1 && email.trim().length < 1) {
                dispatch(warning('Required fields arent present'));
              }
              if (isSignUp && repeatedPassword.trim() !== password) {
                dispatch(danger('Passwords must match'));
              }
              const { payload } = await dispatch(
                !isSignUp
                  ? loginUser({ email, password })
                  : registerUser({ email, password, repeatedPassword })
              );
              if (payload.auth) {
                dispatch(getUser());
                dispatch(success('U have been suscesfully logged in'));
              } else {
                dispatch(danger('Invalid credentials'));
              }
            }}
            {...buttonClick}
            className='w-full px-4 py-2 rounded-md bg-red-400 cursor-pointer text-white text-xl capitalize hover:bg-red-500 transition-all duration-150'
          >
            Sing {isSignUp ? ' up' : ' in'}
          </motion.button>
        </div>

        <div className='flex items-center justify-between gap-16 '>
          <div className='w-24 h-[1px] rounded-md bg-white' />
          <p className='text-white'>or</p>
          <div className='w-24 h-[1px] rounded-md bg-white' />
        </div>

        <motion.div
          {...buttonClick}
          className='flex items-center justify-center px-20 py-2 gap-5 bg-cardOverlay backdrop-blur-md cursor-pointer rounded-3xl'
        >
          <FcGoogle className='text-3xl' />
          <p className='capitalize text-base text-textColor'>
            Sign in with Google
          </p>
        </motion.div>
      </div>
    </div>
  );
};

export default Login;
