import { IRole } from 'app/shared/model/role.model';

export interface IUserO {
  id?: number;
  userName?: string | null;
  password?: string | null;
  email?: string | null;
  role?: IRole | null;
}

export const defaultValue: Readonly<IUserO> = {};
