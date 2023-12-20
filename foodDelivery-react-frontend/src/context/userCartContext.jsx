import { createContext, useContext, useState } from 'react';

const CartContext = createContext();

export function CartContextProvider({ children }) {
  const [isCartOpen, setIsCartOpen] = useState(false);

  return (
    <CartContext.Provider value={{ setIsCartOpen, isCartOpen }}>
      {children}
    </CartContext.Provider>
  );
}

export function useCartContext() {
  const context = useContext(CartContext);
  if (context === undefined) {
    console.error('Invalid context!');
  }
  return context;
}
