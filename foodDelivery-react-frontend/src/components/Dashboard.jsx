import React from 'react';
import DBLeftSection from './DBLeftSection';
import DBRightSection from './DBRightSection';

const Dashboard = () => {
  return (
    <div className='flex flex-grow items-center h-screen bg-primary'>
      <DBLeftSection />
      <DBRightSection />
    </div>
  );
};

export default Dashboard;
