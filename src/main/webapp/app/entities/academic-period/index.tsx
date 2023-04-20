import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AcademicPeriod from './academic-period';
import AcademicPeriodDetail from './academic-period-detail';
import AcademicPeriodUpdate from './academic-period-update';
import AcademicPeriodDeleteDialog from './academic-period-delete-dialog';

const AcademicPeriodRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AcademicPeriod />} />
    <Route path="new" element={<AcademicPeriodUpdate />} />
    <Route path=":id">
      <Route index element={<AcademicPeriodDetail />} />
      <Route path="edit" element={<AcademicPeriodUpdate />} />
      <Route path="delete" element={<AcademicPeriodDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AcademicPeriodRoutes;
