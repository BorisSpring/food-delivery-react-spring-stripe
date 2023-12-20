import React from 'react';
import Header from './Header';
import HomeContainer from './HomeContainer';
import MainPageFruitSection from './MainPageFruitSection.jsx';
import MenuContainer from './MenuContainer.jsx';

const Main = () => {
  return (
    <main className='px-6 md:px-12 lg:px-24 w-full  min-h-screen flex items-center justify-center flex-col bg-primary'>
      {/* nav */}
      <Header />
      {/* hero */}
      <HomeContainer />
      {/* fruit section */}
      <MainPageFruitSection />
      {/* Cateogires menu */}
      <MenuContainer />
    </main>
  );
};

export default Main;
