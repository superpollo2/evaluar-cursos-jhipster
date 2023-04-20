import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TotalScoreCourse from './total-score-course';
import TotalScoreCourseDetail from './total-score-course-detail';
import TotalScoreCourseUpdate from './total-score-course-update';
import TotalScoreCourseDeleteDialog from './total-score-course-delete-dialog';

const TotalScoreCourseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TotalScoreCourse />} />
    <Route path="new" element={<TotalScoreCourseUpdate />} />
    <Route path=":id">
      <Route index element={<TotalScoreCourseDetail />} />
      <Route path="edit" element={<TotalScoreCourseUpdate />} />
      <Route path="delete" element={<TotalScoreCourseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TotalScoreCourseRoutes;
