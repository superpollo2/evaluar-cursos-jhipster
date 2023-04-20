import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TotalScoreProfesor from './total-score-profesor';
import TotalScoreProfesorDetail from './total-score-profesor-detail';
import TotalScoreProfesorUpdate from './total-score-profesor-update';
import TotalScoreProfesorDeleteDialog from './total-score-profesor-delete-dialog';

const TotalScoreProfesorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TotalScoreProfesor />} />
    <Route path="new" element={<TotalScoreProfesorUpdate />} />
    <Route path=":id">
      <Route index element={<TotalScoreProfesorDetail />} />
      <Route path="edit" element={<TotalScoreProfesorUpdate />} />
      <Route path="delete" element={<TotalScoreProfesorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TotalScoreProfesorRoutes;
