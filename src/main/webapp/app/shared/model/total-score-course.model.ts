import { IAcademicPeriod } from 'app/shared/model/academic-period.model';
import { ICourse } from 'app/shared/model/course.model';
import { IProfesor } from 'app/shared/model/profesor.model';

export interface ITotalScoreCourse {
  id?: number;
  cAverageOne?: number | null;
  cAverageTwo?: number | null;
  cAverageThree?: number | null;
  cAverageFour?: number | null;
  cAverageFive?: number | null;
  academicPeriod?: IAcademicPeriod | null;
  course?: ICourse | null;
  profesor?: IProfesor | null;
}

export const defaultValue: Readonly<ITotalScoreCourse> = {};
