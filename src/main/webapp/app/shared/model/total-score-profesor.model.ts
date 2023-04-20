import { IAcademicPeriod } from 'app/shared/model/academic-period.model';

export interface ITotalScoreProfesor {
  id?: number;
  pAverageOne?: number | null;
  pAverageTwo?: number | null;
  pAverageThree?: number | null;
  pAverageFour?: number | null;
  pAverageFive?: number | null;
  academicPeriod?: IAcademicPeriod | null;
}

export const defaultValue: Readonly<ITotalScoreProfesor> = {};
