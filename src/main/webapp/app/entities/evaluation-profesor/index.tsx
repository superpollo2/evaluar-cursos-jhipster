import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EvaluationProfesor from './evaluation-profesor';
import EvaluationProfesorDetail from './evaluation-profesor-detail';
import EvaluationProfesorUpdate from './evaluation-profesor-update';
import EvaluationProfesorDeleteDialog from './evaluation-profesor-delete-dialog';

const EvaluationProfesorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EvaluationProfesor />} />
    <Route path="new" element={<EvaluationProfesorUpdate />} />
    <Route path=":id">
      <Route index element={<EvaluationProfesorDetail />} />
      <Route path="edit" element={<EvaluationProfesorUpdate />} />
      <Route path="delete" element={<EvaluationProfesorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EvaluationProfesorRoutes;
