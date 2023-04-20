import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserO from './user-o';
import UserODetail from './user-o-detail';
import UserOUpdate from './user-o-update';
import UserODeleteDialog from './user-o-delete-dialog';

const UserORoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserO />} />
    <Route path="new" element={<UserOUpdate />} />
    <Route path=":id">
      <Route index element={<UserODetail />} />
      <Route path="edit" element={<UserOUpdate />} />
      <Route path="delete" element={<UserODeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserORoutes;
