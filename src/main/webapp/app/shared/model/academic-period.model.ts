import dayjs from 'dayjs';
import { IEnrollCourse } from 'app/shared/model/enroll-course.model';
import { ITotalScoreCourse } from 'app/shared/model/total-score-course.model';
import { ITotalScoreProfesor } from 'app/shared/model/total-score-profesor.model';

export interface IAcademicPeriod {
  id?: number;
  initPeriod?: string | null;
  finishPeriod?: string | null;
  enrollCourses?: IEnrollCourse[] | null;
  totalScoreCourses?: ITotalScoreCourse[] | null;
  totalScoreProfesors?: ITotalScoreProfesor[] | null;
}

export const defaultValue: Readonly<IAcademicPeriod> = {};
