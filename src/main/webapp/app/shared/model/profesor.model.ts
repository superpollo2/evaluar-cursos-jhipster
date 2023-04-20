import { IUserO } from 'app/shared/model/user-o.model';
import { IEnrollCourse } from 'app/shared/model/enroll-course.model';
import { ITotalScoreCourse } from 'app/shared/model/total-score-course.model';

export interface IProfesor {
  id?: number;
  profesorName?: string | null;
  user?: IUserO | null;
  enrollCourses?: IEnrollCourse[] | null;
  totalScoreCourses?: ITotalScoreCourse[] | null;
}

export const defaultValue: Readonly<IProfesor> = {};
