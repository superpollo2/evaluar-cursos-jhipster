import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserO } from 'app/shared/model/user-o.model';
import { getEntities as getUserOs } from 'app/entities/user-o/user-o.reducer';
import { IProfesor } from 'app/shared/model/profesor.model';
import { getEntity, updateEntity, createEntity, reset } from './profesor.reducer';

export const ProfesorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userOS = useAppSelector(state => state.userO.entities);
  const profesorEntity = useAppSelector(state => state.profesor.entity);
  const loading = useAppSelector(state => state.profesor.loading);
  const updating = useAppSelector(state => state.profesor.updating);
  const updateSuccess = useAppSelector(state => state.profesor.updateSuccess);

  const handleClose = () => {
    navigate('/profesor');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserOs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...profesorEntity,
      ...values,
      user: userOS.find(it => it.id.toString() === values.user.toString()),
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
          ...profesorEntity,
          user: profesorEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.profesor.home.createOrEditLabel" data-cy="ProfesorCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.profesor.home.createOrEditLabel">Create or edit a Profesor</Translate>
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
                  name="id"
                  required
                  readOnly
                  id="profesor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.profesor.profesorId')}
                id="profesor-profesorId"
                name="profesorId"
                data-cy="profesorId"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.profesor.profesorName')}
                id="profesor-profesorName"
                name="profesorName"
                data-cy="profesorName"
                type="text"
              />
              <ValidatedField
                id="profesor-user"
                name="user"
                data-cy="user"
                label={translate('evaluarCursosApp.profesor.user')}
                type="select"
              >
                <option value="" key="0" />
                {userOS
                  ? userOS.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/profesor" replace color="info">
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

export default ProfesorUpdate;
