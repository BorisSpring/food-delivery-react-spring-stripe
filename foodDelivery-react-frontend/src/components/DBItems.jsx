import { Box, Pagination, Typography } from '@mui/material';
import { format } from 'date-fns';
import {
  MaterialReactTable,
  useMaterialReactTable,
} from 'material-react-table';
import React from 'react';
import { useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { findAllProducts } from '../redux/dashboardReducer';
import store from '../redux/store';

const DBItems = () => {
  const [pagination, setPagination] = useState({
    pageIndex: 0,
    pageSize: 10,
  });
  const items = useSelector((store) => store.dashboard.items);
  const dispatch = useDispatch();

  const handleChange = (event, value) => {
    setPagination(() => ({ pageIndex: value, pageSize: 5 }));
    dispatch(findAllProducts(pagination));
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: 'image',
        size: 10,
        header: 'Image',
        enableEditing: false,
        Cell: ({ cell }) => (
          <img
            src={`http://localhost:8080/api/products/image/${cell.getValue()}`}
            alt='Product Avatar'
            className='w-10 h-10 rounded-full'
          />
        ),
      },
      {
        accessorKey: 'id',
        header: 'Id',
        enableEditing: false,
      },
      {
        accessorKey: 'created',
        header: 'Created Date',
        size: 20,
        enableEditing: false,
        Cell: ({ cell }) => (
          <span>{format(new Date(cell.getValue()), 'dd MMM , yyyy')}</span>
        ),
      },
      {
        accessorKey: 'price',
        size: 10,
        header: 'Price',
        enableEditing: false,
        Cell: ({ cell }) => (
          <span className='font-bold'>{cell.getValue()} &euro;</span>
        ),
      },
      {
        accessorKey: 'name',
        size: 15,
        header: 'Name',
        enableEditing: false,
      },
      {
        accessorKey: 'status',
        size: 15,
        header: 'Status',
        enableEditing: false,
        Cell: ({ cell }) => (
          <span
            className={`${cell.getValue ? 'text-green-600' : 'text-red-600'}`}
          >
            {cell.getValue() ? 'Enabled' : 'Disabled'}
          </span>
        ),
      },
    ],
    []
  );

  const table = useMaterialReactTable({
    columns,
    data: items?.content,
    enableEditing: false,
    enableExpandAll: true,
    enableExpanding: true,
    enablePagination: false,
    renderDetailPanel: ({ row }) => (
      <Box
        sx={{
          display: 'flex',
          gap: '1rem',
          flexDirection: 'column',
          fontSize: '14px',
        }}
        key={row.id}
      >
        <Typography variant='h6' sx={{ fontSize: '14px' }}>
          <span className='font-bold'> Ingredients: </span>
          {row.original.ingredients.join(', ')}
        </Typography>
        <Typography variant='h6' sx={{ fontSize: '14px' }}>
          <span className='font-bold'> Calories:</span> {row.original.calories}
        </Typography>
      </Box>
    ),
  });

  return (
    <div className='p-2 md:p-5 lg:p-10'>
      <MaterialReactTable table={table} />
      {items.totalPages > 1 && (
        <div className='w-fit mx-auto mt-5 md:mt-8'>
          <Pagination
            onChange={handleChange}
            count={items.totalPages}
            size='small'
            variant='outlined'
            color='secondary'
          />
        </div>
      )}
    </div>
  );
};

store.dispatch(findAllProducts({ pageIndex: 0, pageSize: 5 }));

export default DBItems;
