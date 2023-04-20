import userO from 'app/entities/user-o/user-o.reducer';
import student from 'app/entities/student/student.reducer';
import academicProgram from 'app/entities/academic-program/academic-program.reducer';
import profesor from 'app/entities/profesor/profesor.reducer';
import enrollCourse from 'app/entities/enroll-course/enroll-course.reducer';
import role from 'app/entities/role/role.reducer';
import course from 'app/entities/course/course.reducer';
import courseQuestion from 'app/entities/course-question/course-question.reducer';
import profesorQuestion from 'app/entities/profesor-question/profesor-question.reducer';
import academicPeriod from 'app/entities/academic-period/academic-period.reducer';
import evaluationCourse from 'app/entities/evaluation-course/evaluation-course.reducer';
import evaluationProfesor from 'app/entities/evaluation-profesor/evaluation-profesor.reducer';
import totalScoreCourse from 'app/entities/total-score-course/total-score-course.reducer';
import totalScoreProfesor from 'app/entities/total-score-profesor/total-score-profesor.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  userO,
  student,
  academicProgram,
  profesor,
  enrollCourse,
  role,
  course,
  courseQuestion,
  profesorQuestion,
  academicPeriod,
  evaluationCourse,
  evaluationProfesor,
  totalScoreCourse,
  totalScoreProfesor,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
