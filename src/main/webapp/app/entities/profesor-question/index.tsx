import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProfesorQuestion from './profesor-question';
import ProfesorQuestionDetail from './profesor-question-detail';
import ProfesorQuestionUpdate from './profesor-question-update';
import ProfesorQuestionDeleteDialog from './profesor-question-delete-dialog';

const ProfesorQuestionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProfesorQuestion />} />
    <Route path="new" element={<ProfesorQuestionUpdate />} />
    <Route path=":id">
      <Route index element={<ProfesorQuestionDetail />} />
      <Route path="edit" element={<ProfesorQuestionUpdate />} />
      <Route path="delete" element={<ProfesorQuestionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProfesorQuestionRoutes;
