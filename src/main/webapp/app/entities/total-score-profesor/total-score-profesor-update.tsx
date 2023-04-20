import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAcademicPeriod } from 'app/shared/model/academic-period.model';
import { getEntities as getAcademicPeriods } from 'app/entities/academic-period/academic-period.reducer';
import { ITotalScoreProfesor } from 'app/shared/model/total-score-profesor.model';
import { getEntity, updateEntity, createEntity, reset } from './total-score-profesor.reducer';

export const TotalScoreProfesorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const academicPeriods = useAppSelector(state => state.academicPeriod.entities);
  const totalScoreProfesorEntity = useAppSelector(state => state.totalScoreProfesor.entity);
  const loading = useAppSelector(state => state.totalScoreProfesor.loading);
  const updating = useAppSelector(state => state.totalScoreProfesor.updating);
  const updateSuccess = useAppSelector(state => state.totalScoreProfesor.updateSuccess);

  const handleClose = () => {
    navigate('/total-score-profesor');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAcademicPeriods({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...totalScoreProfesorEntity,
      ...values,
      academicPeriod: academicPeriods.find(it => it.id.toString() === values.academicPeriod.toString()),
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
          ...totalScoreProfesorEntity,
          academicPeriod: totalScoreProfesorEntity?.academicPeriod?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.totalScoreProfesor.home.createOrEditLabel" data-cy="TotalScoreProfesorCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.totalScoreProfesor.home.createOrEditLabel">
              Create or edit a TotalScoreProfesor
            </Translate>
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
                  id="total-score-profesor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreProfesor.pAverageOne')}
                id="total-score-profesor-pAverageOne"
                name="pAverageOne"
                data-cy="pAverageOne"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreProfesor.pAverageTwo')}
                id="total-score-profesor-pAverageTwo"
                name="pAverageTwo"
                data-cy="pAverageTwo"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreProfesor.pAverageThree')}
                id="total-score-profesor-pAverageThree"
                name="pAverageThree"
                data-cy="pAverageThree"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreProfesor.pAverageFour')}
                id="total-score-profesor-pAverageFour"
                name="pAverageFour"
                data-cy="pAverageFour"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreProfesor.pAverageFive')}
                id="total-score-profesor-pAverageFive"
                name="pAverageFive"
                data-cy="pAverageFive"
                type="text"
              />
              <ValidatedField
                id="total-score-profesor-academicPeriod"
                name="academicPeriod"
                data-cy="academicPeriod"
                label={translate('evaluarCursosApp.totalScoreProfesor.academicPeriod')}
                type="select"
              >
                <option value="" key="0" />
                {academicPeriods
                  ? academicPeriods.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/total-score-profesor" replace color="info">
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

export default TotalScoreProfesorUpdate;
