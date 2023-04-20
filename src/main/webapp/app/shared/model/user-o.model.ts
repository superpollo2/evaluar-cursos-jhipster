import { IRole } from 'app/shared/model/role.model';

export interface IUserO {
  userName?: string;
  password?: string | null;
  email?: string | null;
  role?: IRole | null;
}

export const defaultValue: Readonly<IUserO> = {};
