import React from 'react';
import { useDispatch } from 'react-redux';
import { useMemo } from 'react';
import store from '../redux/store';
import {
  MaterialReactTable,
  useMaterialReactTable,
} from 'material-react-table';
import { useSelector } from 'react-redux';
import {
  deleteUser,
  enableDisableUser,
  findAllUsers,
} from '../redux/dashboardReducer';
import { Grid, ListItemIcon, MenuItem } from '@mui/material';
import { Delete, Edit } from '@mui/icons-material';
import { format } from 'date-fns';

const orders = {
  DELIVERED: 'green',
  CANCELED: 'red',
  RETURNED: 'blue',
  DELIVERING: 'emerald',
};
const DBUsers = () => {
  const dispatch = useDispatch();
  const users = useSelector((store) => store.dashboard.users);

  const columns = useMemo(
    () => [
      {
        accessorKey: 'imageName',
        header: 'Image',
        enableEditing: false,
        size: 15,
        Cell: ({ cell }) => (
          <img
            className='w-10 h-10 rounded-full'
            alt='User Avatar'
            src={`http://localhost:8080/api/products/image/${cell.getValue()}`}
          />
        ),
      },
      {
        accessorKey: 'id',
        header: 'Id',
        enableEditing: false,
        size: 5,
      },
      {
        accessorKey: 'email',
        header: 'Email Adress',
        enableEditing: false,
        size: 35,
      },
      {
        accessorKey: 'firstName',
        header: 'First Name',
        size: 25,
        enableEditing: false,
      },
      {
        accessorKey: 'lastName',
        header: 'Last Name',
        size: 30,
        enableEditing: false,
      },
      {
        accessorKey: 'authority',
        header: 'Position',
        size: 25,
        enableEditing: false,
      },
      {
        accessorKey: 'enabled',
        header: 'Status',
        size: 10,
        Cell: ({ cell }) => (
          <span className={cell.getValue() ? 'text-green-600' : 'text-red-600'}>
            {cell.getValue() ? 'Enabled' : 'Disabled'}
          </span>
        ),
      },
    ],
    []
  );

  const table = useMaterialReactTable({
    columns,
    data: users?.content,
    enableColumnActions: true,
    enableEditing: false,
    enableExpandAll: true,
    enableExpanding: true,
    enableRowActions: true,
    renderDetailPanel: ({ row }) =>
      row.original.orders.map((order) => (
        <Grid container spacing={2} sx={{ marginBottom: '15px' }}>
          <Grid item sx={{ fontSize: '13px' }} xs={6} md={2} lg={1.7}>
            Ordered At: {format(new Date(order.created), '  dd MMM , HH:mm a')}
          </Grid>
          <Grid item xs={6} md={1.8} lg={1.5} sx={{ fontSize: '13px' }}>
            Delivered At: {format(new Date(order.deliveredTime), ' HH:mm a')}
          </Grid>
          <Grid item xs={6} md={2.3} lg={2} sx={{ fontSize: '13px' }}>
            Estimated Delivery Time:{' '}
            {format(new Date(order.deliveredTime), ' HH:mm a')}
          </Grid>
          <Grid item xs={6} md={2} lg={1.7} sx={{ fontSize: '13px' }}>
            Order Status:{' '}
            <span className={`text-${orders[order.orderStatus]}-400`}>
              {order.orderStatus}
            </span>
          </Grid>
          <Grid item xs={6} md={2} lg={1.7} sx={{ fontSize: '13px' }}>
            delivery Adress: {order.deliveryAdress}{' '}
          </Grid>
          <Grid item xs={6} md={2} lg={1.7} sx={{ fontSize: '13px' }}>
            Number: {order.phoneNumber}
          </Grid>
          <Grid item xs={6} md={2} lg={1.7} sx={{ fontSize: '13px' }}>
            Total Price: {order.totalPrice.toFixed(2)} &euro;
          </Grid>
        </Grid>
      )),
    renderRowActionMenuItems: ({ closeMenu, row }) => {
      return [
        <MenuItem
          key={2}
          sx={{ m: 0 }}
          onClick={async () => {
            if (
              window.confirm(
                'Do u want to delete user with email ' +
                  row.original.email +
                  '?'
              )
            ) {
              await dispatch(deleteUser(row.original.id));
            }
            closeMenu();
          }}
        >
          <ListItemIcon>
            <Delete />
          </ListItemIcon>
          Delete User
        </MenuItem>,
        <MenuItem
          key={3}
          onClick={async () => {
            if (
              window.confirm(
                `Do you want to ${
                  row.original.enabled ? 'disable' : 'enable'
                } user with email ${row.original.email}?`
              )
            ) {
              await dispatch(enableDisableUser(row.original.id));
            }
            closeMenu();
          }}
        >
          <ListItemIcon>
            <Edit />
          </ListItemIcon>
          {row.original.enabled ? 'Disable' : 'Enable'}
        </MenuItem>,
      ];
    },
  });

  return (
    <div className='p-2 md:p-5 lg:p-10'>
      <MaterialReactTable table={table} />
    </div>
  );
};

store.dispatch(findAllUsers({ pageNumber: 1, pageSize: 5 }));

export default DBUsers;
