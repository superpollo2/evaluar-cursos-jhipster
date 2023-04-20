import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CourseQuestion from './course-question';
import CourseQuestionDetail from './course-question-detail';
import CourseQuestionUpdate from './course-question-update';
import CourseQuestionDeleteDialog from './course-question-delete-dialog';

const CourseQuestionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CourseQuestion />} />
    <Route path="new" element={<CourseQuestionUpdate />} />
    <Route path=":id">
      <Route index element={<CourseQuestionDetail />} />
      <Route path="edit" element={<CourseQuestionUpdate />} />
      <Route path="delete" element={<CourseQuestionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CourseQuestionRoutes;
