import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserO from './user-o';
import Student from './student';
import AcademicProgram from './academic-program';
import Profesor from './profesor';
import EnrollCourse from './enroll-course';
import Role from './role';
import Course from './course';
import CourseQuestion from './course-question';
import ProfesorQuestion from './profesor-question';
import AcademicPeriod from './academic-period';
import EvaluationCourse from './evaluation-course';
import EvaluationProfesor from './evaluation-profesor';
import TotalScoreCourse from './total-score-course';
import TotalScoreProfesor from './total-score-profesor';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="user-o/*" element={<UserO />} />
        <Route path="student/*" element={<Student />} />
        <Route path="academic-program/*" element={<AcademicProgram />} />
        <Route path="profesor/*" element={<Profesor />} />
        <Route path="enroll-course/*" element={<EnrollCourse />} />
        <Route path="role/*" element={<Role />} />
        <Route path="course/*" element={<Course />} />
        <Route path="course-question/*" element={<CourseQuestion />} />
        <Route path="profesor-question/*" element={<ProfesorQuestion />} />
        <Route path="academic-period/*" element={<AcademicPeriod />} />
        <Route path="evaluation-course/*" element={<EvaluationCourse />} />
        <Route path="evaluation-profesor/*" element={<EvaluationProfesor />} />
        <Route path="total-score-course/*" element={<TotalScoreCourse />} />
        <Route path="total-score-profesor/*" element={<TotalScoreProfesor />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
