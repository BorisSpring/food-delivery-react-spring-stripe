import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { addNewProduct } from '../redux/dashboardReducer';

const initialState = {
  image: null,
  price: 0,
  categoryId: '',
  itemName: '',
  calories: '',
  description: '',
  ingredients: [],
};

const DBNewItem = () => {
  const [product, setProduct] = useState(initialState);
  const [ingredients, setIngredients] = useState('');
  const categories = useSelector((store) => store.products.categories);
  const dispatch = useDispatch();

  const handleChnage = (fieldName, value) => {
    setProduct((prev) => ({ ...prev, [fieldName]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(addNewProduct(product));
    setProduct(initialState);
  };

  return (
    <div className='mx-4 md:mx-10  overflow-x-hidden'>
      <form
        onSubmit={(e) => handleSubmit(e)}
        className='w-full flex flex-col gap-1 max-w-[800px] m-auto mt-10'
      >
        <div className='flex flex-col gap-1'>
          <input
            type='file'
            id='image'
            className='hidden'
            onChange={(e) =>
              setProduct((prev) => ({ ...prev, image: e.target.files[0] }))
            }
          />
          <label htmlFor='image'>Click to Upload Image</label>
          {product.image && (
            <img src={URL.createObjectURL(product.image)} alt='Product Art' />
          )}
        </div>
        <div className='flex flex-col gap-1'>
          <label htmlFor='ingredients'>Product ingredients:</label>
          {product.ingredients?.length > 0 && (
            <ol className='flex flex-col gap1-'>
              {product.ingredients.map((ingredient, index) => (
                <li key={index}>
                  {index + 1}. {ingredient}
                </li>
              ))}
            </ol>
          )}
          <input
            value={ingredients}
            onChange={(e) => setIngredients(e.target.value)}
            type='text'
            className='rounded-md px-2 py-1 text-headingColor border-2 border-red-300 focus:ring-1 focus:ring-red-200 outline-none focus:shadow-red-400 focus:shadow-md '
          />
          <button
            className='w-full mt-2 bg-red-400 text-white focus:ring focus:ring-opacity-80 focus:ring-red-200 p-1 rounded-md hover:red-500 transition-all duration-300 hover:shadow-md'
            onClick={(e) => {
              e.stopPropagation();
              e.preventDefault();
              if (ingredients.trim()?.length > 0) {
                setProduct((prev) => ({
                  ...prev,
                  ingredients: [
                    ...(prev.ingredients || []),
                    ingredients.trim(),
                  ],
                }));
                setIngredients('');
              }
            }}
          >
            Add ingredient to product
          </button>
        </div>
        <div className='flex flex-col gap-1'>
          <label htmlFor='itemName'>Product Title</label>
          <input
            required
            value={product.itemName}
            className='rounded-md px-2 py-1 text-headingColor border-2 border-red-300 focus:ring-1 focus:ring-red-200 outline-none focus:shadow-red-400 focus:shadow-md '
            type='text'
            onChange={(e) => handleChnage('itemName', e.target.value)}
          />
        </div>
        <div className='flex flex-col gap-1'>
          <label htmlFor='description'>Product description</label>
          <input
            required
            value={product.description}
            className='rounded-md px-2 py-1 text-headingColor border-2 border-red-300 focus:ring-1 focus:ring-red-200 outline-none focus:shadow-red-400 focus:shadow-md '
            type='text'
            onChange={(e) => handleChnage('description', e.target.value)}
          />
        </div>
        <div className='flex flex-col gap-1'>
          <label htmlFor='calories'>Product Calories</label>
          <input
            required
            value={product.calories}
            className='rounded-md px-2 py-1 text-headingColor border-2 border-red-300 focus:ring-1 focus:ring-red-200 outline-none focus:shadow-red-400 focus:shadow-md '
            id='calories'
            type='text'
            onChange={(e) => handleChnage('calories', e.target.value)}
          />
        </div>
        <div className='flex flex-col gap-1'>
          <label htmlFor='price'>Product price</label>
          <input
            required
            value={product.price}
            className='rounded-md px-2 py-1 text-headingColor border-2 border-red-300 focus:ring-1 focus:ring-red-200 outline-none focus:shadow-red-400 focus:shadow-md '
            id='price'
            type='number'
            step={0.1}
            onChange={(e) => handleChnage('price', parseFloat(e.target.value))}
          />
        </div>
        <div className='flex flex-col gap-1 my-5'>
          <label htmlFor='categoryId'>
            {product.categoryId
              ? `Selected Category is ${
                  categories.find(
                    (category) => category.id === product.categoryId
                  ).name
                }`
              : 'Please Select Product Category'}
          </label>
          <ul className='flex flex-col'>
            {categories
              .filter((category) => category.id !== product?.categoryId)
              .map(({ id, name }, index) => (
                <li onClick={() => handleChnage('categoryId', id)} key={id}>
                  <span className='font-bold'>{index + 1}.</span> {name}
                </li>
              ))}
          </ul>
        </div>

        <button
          className='w-full mt-2 bg-red-400 text-white focus:ring focus:ring-opacity-80 focus:ring-red-200 p-1 rounded-md hover:red-500 transition-all duration-300 hover:shadow-md'
          type='submit'
        >
          Add New Product
        </button>
      </form>
    </div>
  );
};

export default DBNewItem;
