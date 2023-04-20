import { IEnrollCourse } from 'app/shared/model/enroll-course.model';

export interface IEvaluationCourse {
  id?: number;
  cScoreQuestionOne?: number | null;
  cScoreQuestionTwo?: number | null;
  cScoreQuestionThree?: number | null;
  cScoreQuestionFour?: number | null;
  cScoreQuestionFive?: number | null;
  cScoreQuestionSix?: number | null;
  enrollCourse?: IEnrollCourse | null;
}

export const defaultValue: Readonly<IEvaluationCourse> = {};
