import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAcademicPeriod } from 'app/shared/model/academic-period.model';
import { getEntity, updateEntity, createEntity, reset } from './academic-period.reducer';

export const AcademicPeriodUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const academicPeriodEntity = useAppSelector(state => state.academicPeriod.entity);
  const loading = useAppSelector(state => state.academicPeriod.loading);
  const updating = useAppSelector(state => state.academicPeriod.updating);
  const updateSuccess = useAppSelector(state => state.academicPeriod.updateSuccess);

  const handleClose = () => {
    navigate('/academic-period');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.initPeriod = convertDateTimeToServer(values.initPeriod);
    values.finishPeriod = convertDateTimeToServer(values.finishPeriod);

    const entity = {
      ...academicPeriodEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          initPeriod: displayDefaultDateTime(),
          finishPeriod: displayDefaultDateTime(),
        }
      : {
          ...academicPeriodEntity,
          initPeriod: convertDateTimeFromServer(academicPeriodEntity.initPeriod),
          finishPeriod: convertDateTimeFromServer(academicPeriodEntity.finishPeriod),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.academicPeriod.home.createOrEditLabel" data-cy="AcademicPeriodCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.academicPeriod.home.createOrEditLabel">Create or edit a AcademicPeriod</Translate>
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
                  id="academic-period-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.academicPeriod.initPeriod')}
                id="academic-period-initPeriod"
                name="initPeriod"
                data-cy="initPeriod"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.academicPeriod.finishPeriod')}
                id="academic-period-finishPeriod"
                name="finishPeriod"
                data-cy="finishPeriod"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/academic-period" replace color="info">
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

export default AcademicPeriodUpdate;
