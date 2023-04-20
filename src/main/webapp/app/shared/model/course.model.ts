import { ITotalScoreCourse } from 'app/shared/model/total-score-course.model';

export interface ICourse {
  id?: number;
  courseId?: string | null;
  courseName?: string | null;
  totalScoreCourses?: ITotalScoreCourse[] | null;
}

export const defaultValue: Readonly<ICourse> = {};
