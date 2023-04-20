import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EvaluationCourse from './evaluation-course';
import EvaluationCourseDetail from './evaluation-course-detail';
import EvaluationCourseUpdate from './evaluation-course-update';
import EvaluationCourseDeleteDialog from './evaluation-course-delete-dialog';

const EvaluationCourseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EvaluationCourse />} />
    <Route path="new" element={<EvaluationCourseUpdate />} />
    <Route path=":id">
      <Route index element={<EvaluationCourseDetail />} />
      <Route path="edit" element={<EvaluationCourseUpdate />} />
      <Route path="delete" element={<EvaluationCourseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EvaluationCourseRoutes;
