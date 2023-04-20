import { IStudent } from 'app/shared/model/student.model';
import { IEnrollCourse } from 'app/shared/model/enroll-course.model';

export interface IAcademicProgram {
  id?: number;
  programName?: string | null;
  students?: IStudent[] | null;
  enrollCourses?: IEnrollCourse[] | null;
}

export const defaultValue: Readonly<IAcademicProgram> = {};
