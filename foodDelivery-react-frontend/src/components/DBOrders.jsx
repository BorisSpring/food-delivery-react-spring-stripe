import { Edit } from '@mui/icons-material';
import {
  Box,
  ListItemIcon,
  MenuItem,
  Pagination,
  Typography,
} from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import {
  MaterialReactTable,
  useMaterialReactTable,
} from 'material-react-table';
import React from 'react';
import { useMemo } from 'react';
import { useSelector } from 'react-redux';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import store from '../redux/store';
import {
  findOrders,
  updateOrder,
  updateOrderStatus,
} from '../redux/dashboardReducer';
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { format, formatDistanceToNow } from 'date-fns';

const orderStatusColor = {
  DELIVERED: 'green',
  CANCELED: 'red',
  RETURNED: 'blue',
  DELIVERING: 'emerald',
};

const DBOrders = () => {
  const [page, setPage] = useState(0);
  const orders = useSelector((store) => store.dashboard.orders);
  const [validationErrors, setValidationErrors] = useState({});
  const dispatch = useDispatch();
  const handleChange = (event, value) => {
    setPage(() => value);
    dispatch(findOrders(page));
  };
  const columns = useMemo(
    () => [
      {
        accessorKey: 'id',
        header: 'Id',
        enableEditing: false,
        size: 10,
      },
      {
        accessorKey: 'firstName', //access nested data with dot notation
        header: 'First Name',
        size: 20,
        enableEditing: false,
      },
      {
        accessorKey: 'lastName',
        header: 'Last Name',
        size: 20,
        enableEditing: false,
      },
      {
        accessorKey: 'deliveryAdress', //normal accessorKey
        header: 'Address',
        size: 50,
        muiEditTextFieldProps: {
          type: 'text',
          required: true,
          error: !!validationErrors?.firstName,
          helperText: validationErrors?.firstName,
          onFocus: () =>
            setValidationErrors({
              ...validationErrors,
              firstName: undefined,
            }),
        },
      },
      {
        accessorKey: 'customerEmail',
        header: 'Email',
        size: 10,
        enableEditing: false,
      },
      {
        accessorKey: 'totalPrice',
        header: 'Price',
        minWidth: 10,
        size: 5,
        enableEditing: false,
        Cell: ({ cell }) => (
          <span className='font-bold text-black'>
            {cell.getValue().toFixed(2)} &euro;
          </span>
        ),
      },
      {
        accessorKey: 'orderStatus',
        header: 'orderStatus',
        size: 40,
        enableEditing: false,
        Cell: ({ cell }) => (
          <span
            style={{
              color: orderStatusColor[cell.getValue()],
              fontSize: 'bold',
            }}
            className='font-bold'
          >
            {cell.getValue()}
          </span>
        ),
      },
      {
        accessorKey: 'phoneNumber',
        header: 'Phone Number',
        size: 40,
        muiEditTextFieldProps: {
          type: 'text',
          required: true,
          error: !!validationErrors?.firstName,
          helperText: validationErrors?.firstName,
          onFocus: () =>
            setValidationErrors({
              ...validationErrors,
              firstName: undefined,
            }),
        },
      },
      {
        accessorKey: 'created',
        header: 'Ordered At',
        size: 40,
        enableEditing: false,
        Cell: ({ cell }) => (
          <span className='headingColor'>
            {formatDistanceToNow(new Date(cell.getValue()), {
              addSuffix: true,
            })}
          </span>
        ),
      },
      {
        accessorKey: 'estimatedDeliveryTime',
        header: 'Estimated Delivery Time',
        size: 20,
        enableEditing: false,
        Cell: ({ cell }) => (
          <span className='headingColor '>
            {format(new Date(cell.getValue()), 'HH:mm b')}
          </span>
        ),
      },
      {
        accessorKey: 'deliveredTime',
        header: 'Delivered At',
        size: 40,
        enableEditing: false,
        Cell: ({ cell }) => (
          <span className='headingColor'>
            {format(new Date(cell.getValue()), 'HH:mm b')}
          </span>
        ),
      },
    ],
    [validationErrors]
  );

  const handleEditOrder = async ({ values, table }) => {
    const newValidationErrors = validateOrder(values);
    if (Object.values(newValidationErrors).some((error) => error)) {
      setValidationErrors(newValidationErrors);
      return;
    }
    setValidationErrors({});
    await dispatch(updateOrder(values));
    table.setEditingRow(null); //exit editing mode
  };

  const validateRequired = (value) => !!value.length;
  const validateEmail = (email) =>
    !!email.length &&
    email
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      );

  function validateOrder(order) {
    return {
      firstName: !validateRequired(order.firstName)
        ? 'First Name is Required'
        : '',
      lastName: !validateRequired(order.lastName)
        ? 'Last Name is Required'
        : '',
      email: !validateEmail(order.customerEmail)
        ? 'Incorrect Email Format'
        : '',
      phoneNumber: !validateRequired(order.phoneNumber),
    };
  }

  const table = useMaterialReactTable({
    columns,
    data: orders?.content,
    createDisplayMode: 'modal', //default ('row', and 'custom' are also available)
    editDisplayMode: 'modal', //default ('row', 'cell', 'table', and 'custom' are also available)
    enableEditing: true,
    onEditingRowCancel: () => setValidationErrors({}),
    onEditingRowSave: handleEditOrder,
    enableExpanding: true,
    renderDetailPanel: ({ row }) =>
      row.original.orderItems?.map((orderItem, index) => (
        <Box
          key={index}
          sx={{
            display: 'flex',
            gap: '2rem',
            width: '100%',
          }}
        >
          <img
            src={`http://localhost:8080/api/products/image/${orderItem.image}`}
            alt='Product Avatar'
            className='w-10 h-10 rounded-full'
          />
          <Typography sx={{ fontSize: '12px' }}>
            Product Name: {orderItem.name}
          </Typography>
          <Typography sx={{ fontSize: '12px' }}>
            Quantity: {orderItem.quantity}
          </Typography>
          <Typography sx={{ fontSize: '12px' }}>
            Total Order Item price:{' '}
            <span className='font-bold'>
              {' '}
              {orderItem.totalPrice.toFixed(2)} &euro;
            </span>
          </Typography>
        </Box>
      )),
    getRowId: (row) => row.id,
    renderRowActionMenuItems: ({ closeMenu, row }) => {
      const handleCancelOrder = () => {
        if (
          window.confirm(
            'Confirm order cancel for order with id ' + row.original.id
          )
        ) {
          dispatch(
            updateOrderStatus({
              orderId: row.original.id,
              orderStatus: 'CANCELED',
            })
          );
        }
        closeMenu();
      };
      const handleReturnOrder = () => {
        if (
          window.confirm(
            'Confirm for order with  id ' +
              row.original.id +
              ' that is returned?'
          )
        ) {
          dispatch(
            updateOrderStatus({
              orderId: row.original.id,
              orderStatus: 'RETURNED',
            })
          );
        }
        closeMenu();
      };

      const handleDelivered = () => {
        if (
          window.confirm(
            'Confirm for order with  id ' +
              row.original.id +
              ' that is delivered?'
          )
        ) {
          dispatch(
            updateOrderStatus({
              orderId: row.original.id,
              orderStatus: 'DELIVERED',
            })
          );
        }
        closeMenu();
      };
      const items = [];
      if (
        row.original.orderStatus !== 'RETURNED' &&
        row.original.orderStatus !== 'DELIVERED' &&
        row.original.orderStatus !== 'CANCELED'
      ) {
        items.push(
          <MenuItem key={2} onClick={handleCancelOrder} sx={{ m: 0 }}>
            <ListItemIcon>
              <Edit />
            </ListItemIcon>
            Mark as Canceled
          </MenuItem>
        );
      }
      if (
        row.original.orderStatus !== 'CANCELED' &&
        row.original.orderStatus !== 'RETURNED'
      ) {
        items.push(
          <MenuItem key={3} onClick={handleReturnOrder} sx={{ m: 0 }}>
            <ListItemIcon>
              <Edit />
            </ListItemIcon>
            Mark as Returned
          </MenuItem>
        );
      }
      if (
        row.original.orderStatus !== 'CANCELED' &&
        row.original.orderStatus !== 'RETURNED' &&
        row.original.orderStatus !== 'DELIVERED'
      ) {
        items.push(
          <MenuItem key={4} onClick={handleDelivered} sx={{ m: 0 }}>
            <ListItemIcon>
              <Edit />
            </ListItemIcon>
            Mark as Delivered
          </MenuItem>
        );
      }
      return items;
    },
  });

  return (
    <div className='p-2 md:p-5 lg:p-10 w-full '>
      <MaterialReactTable table={table} />
      {orders?.totalPages > 1 && (
        <div className='w-fit mx-auto mt-5 md:mt-8'>
          <Pagination
            page={page}
            onChange={handleChange}
            count={orders.totalPages}
            size='small'
            variant='outlined'
            color='secondary'
          />
        </div>
      )}
    </div>
  );
};

const ExampleWithLocalizationProvider = () => (
  <LocalizationProvider dateAdapter={AdapterDayjs}>
    <DBOrders />
  </LocalizationProvider>
);
export default ExampleWithLocalizationProvider;

store.dispatch(findOrders(0));
