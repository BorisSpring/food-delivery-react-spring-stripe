import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { fadeInOut } from '../animations/variants';

const LoginInput = ({
  placeholder,
  icon,
  inputState,
  inputStatefunc,
  type,
  isSignUp,
}) => {
  const [isFocus, setIsFocus] = useState(false);

  return (
    <motion.div
      {...fadeInOut}
      className={`flex w-full backdrop-blur-md bg-[rgba(255,255,255,0.4)] items-center justify-center gap-4 py-1   px-4 rounded-md ${
        isFocus ? 'shadow-md shadow-red-400' : 'shadow-none'
      }`}
    >
      {icon}
      <input
        autoComplete='off'
        type={type}
        className='w-full h-full bg-transparent text-headingColor text-lg font-semibold outline-none'
        placeholder={placeholder}
        value={inputState}
        onFocus={() => setIsFocus((prev) => !prev)}
        onBlur={() => setIsFocus((prev) => !prev)}
        onChange={(e) => inputStatefunc(e.target.value)}
      />
    </motion.div>
  );
};

export default LoginInput;
