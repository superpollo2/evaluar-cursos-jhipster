import { IUserO } from 'app/shared/model/user-o.model';
import { IEnrollCourse } from 'app/shared/model/enroll-course.model';
import { IAcademicProgram } from 'app/shared/model/academic-program.model';

export interface IStudent {
  id?: number;
  studentName?: string | null;
  user?: IUserO | null;
  enrollCourses?: IEnrollCourse[] | null;
  program?: IAcademicProgram | null;
}

export const defaultValue: Readonly<IStudent> = {};
