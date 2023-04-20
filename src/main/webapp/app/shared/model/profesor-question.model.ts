export interface IProfesorQuestion {
  id?: number;
  questionId?: string | null;
  profesorQuestion?: string | null;
}

export const defaultValue: Readonly<IProfesorQuestion> = {};
