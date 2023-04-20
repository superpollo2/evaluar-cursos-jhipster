export interface ICourseQuestion {
  id?: number;
  questionId?: string | null;
  courseQuestion?: string | null;
}

export const defaultValue: Readonly<ICourseQuestion> = {};
