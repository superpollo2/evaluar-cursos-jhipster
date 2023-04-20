import { IStudent } from 'app/shared/model/student.model';
import { IAcademicProgram } from 'app/shared/model/academic-program.model';
import { IProfesor } from 'app/shared/model/profesor.model';
import { IAcademicPeriod } from 'app/shared/model/academic-period.model';

export interface IEnrollCourse {
  id?: number;
  periodAcademic?: string | null;
  isEvaluated?: number | null;
  student?: IStudent | null;
  academicProgram?: IAcademicProgram | null;
  profesor?: IProfesor | null;
  academicPeriod?: IAcademicPeriod | null;
}

export const defaultValue: Readonly<IEnrollCourse> = {};
