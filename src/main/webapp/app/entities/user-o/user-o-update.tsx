import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRole } from 'app/shared/model/role.model';
import { getEntities as getRoles } from 'app/entities/role/role.reducer';
import { IUserO } from 'app/shared/model/user-o.model';
import { getEntity, updateEntity, createEntity, reset } from './user-o.reducer';

export const UserOUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const roles = useAppSelector(state => state.role.entities);
  const userOEntity = useAppSelector(state => state.userO.entity);
  const loading = useAppSelector(state => state.userO.loading);
  const updating = useAppSelector(state => state.userO.updating);
  const updateSuccess = useAppSelector(state => state.userO.updateSuccess);

  const handleClose = () => {
    navigate('/user-o');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRoles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...userOEntity,
      ...values,
      role: roles.find(it => it.id.toString() === values.role.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...userOEntity,
          role: userOEntity?.role?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.userO.home.createOrEditLabel" data-cy="UserOCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.userO.home.createOrEditLabel">Create or edit a UserO</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="userName"
                  required
                  readOnly
                  id="user-o-userName"
                  label={translate('evaluarCursosApp.userO.userName')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.userO.password')}
                id="user-o-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.userO.email')}
                id="user-o-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{}}
              />
              <ValidatedField id="user-o-role" name="role" data-cy="role" label={translate('evaluarCursosApp.userO.role')} type="select">
                <option value="" key="0" />
                {roles
                  ? roles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-o" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UserOUpdate;
