import { IUserO } from 'app/shared/model/user-o.model';

export interface IRole {
  id?: number;
  roleName?: string | null;
  userOS?: IUserO[] | null;
}

export const defaultValue: Readonly<IRole> = {};
