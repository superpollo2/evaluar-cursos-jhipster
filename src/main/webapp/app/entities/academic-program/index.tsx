import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AcademicProgram from './academic-program';
import AcademicProgramDetail from './academic-program-detail';
import AcademicProgramUpdate from './academic-program-update';
import AcademicProgramDeleteDialog from './academic-program-delete-dialog';

const AcademicProgramRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AcademicProgram />} />
    <Route path="new" element={<AcademicProgramUpdate />} />
    <Route path=":id">
      <Route index element={<AcademicProgramDetail />} />
      <Route path="edit" element={<AcademicProgramUpdate />} />
      <Route path="delete" element={<AcademicProgramDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AcademicProgramRoutes;
