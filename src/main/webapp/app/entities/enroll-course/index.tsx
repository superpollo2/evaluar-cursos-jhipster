import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EnrollCourse from './enroll-course';
import EnrollCourseDetail from './enroll-course-detail';
import EnrollCourseUpdate from './enroll-course-update';
import EnrollCourseDeleteDialog from './enroll-course-delete-dialog';

const EnrollCourseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EnrollCourse />} />
    <Route path="new" element={<EnrollCourseUpdate />} />
    <Route path=":id">
      <Route index element={<EnrollCourseDetail />} />
      <Route path="edit" element={<EnrollCourseUpdate />} />
      <Route path="delete" element={<EnrollCourseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EnrollCourseRoutes;
