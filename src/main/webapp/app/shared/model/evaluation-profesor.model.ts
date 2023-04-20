import { IEnrollCourse } from 'app/shared/model/enroll-course.model';

export interface IEvaluationProfesor {
  id?: number;
  pScoreQuestionOne?: number | null;
  pScoreQuestionTwo?: number | null;
  pScoreQuestionThree?: number | null;
  pScoreQuestionFour?: number | null;
  pScoreQuestionFive?: number | null;
  pScoreQuestionSix?: number | null;
  enrollCourse?: IEnrollCourse | null;
}

export const defaultValue: Readonly<IEvaluationProfesor> = {};
