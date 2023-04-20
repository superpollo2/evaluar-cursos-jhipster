import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEnrollCourse } from 'app/shared/model/enroll-course.model';
import { getEntities as getEnrollCourses } from 'app/entities/enroll-course/enroll-course.reducer';
import { IEvaluationProfesor } from 'app/shared/model/evaluation-profesor.model';
import { getEntity, updateEntity, createEntity, reset } from './evaluation-profesor.reducer';

export const EvaluationProfesorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const enrollCourses = useAppSelector(state => state.enrollCourse.entities);
  const evaluationProfesorEntity = useAppSelector(state => state.evaluationProfesor.entity);
  const loading = useAppSelector(state => state.evaluationProfesor.loading);
  const updating = useAppSelector(state => state.evaluationProfesor.updating);
  const updateSuccess = useAppSelector(state => state.evaluationProfesor.updateSuccess);

  const handleClose = () => {
    navigate('/evaluation-profesor');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEnrollCourses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...evaluationProfesorEntity,
      ...values,
      enrollCourse: enrollCourses.find(it => it.id.toString() === values.enrollCourse.toString()),
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
          ...evaluationProfesorEntity,
          enrollCourse: evaluationProfesorEntity?.enrollCourse?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.evaluationProfesor.home.createOrEditLabel" data-cy="EvaluationProfesorCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.evaluationProfesor.home.createOrEditLabel">
              Create or edit a EvaluationProfesor
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
                  id="evaluation-profesor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationProfesor.pScoreQuestionOne')}
                id="evaluation-profesor-pScoreQuestionOne"
                name="pScoreQuestionOne"
                data-cy="pScoreQuestionOne"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationProfesor.pScoreQuestionTwo')}
                id="evaluation-profesor-pScoreQuestionTwo"
                name="pScoreQuestionTwo"
                data-cy="pScoreQuestionTwo"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationProfesor.pScoreQuestionThree')}
                id="evaluation-profesor-pScoreQuestionThree"
                name="pScoreQuestionThree"
                data-cy="pScoreQuestionThree"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationProfesor.pScoreQuestionFour')}
                id="evaluation-profesor-pScoreQuestionFour"
                name="pScoreQuestionFour"
                data-cy="pScoreQuestionFour"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationProfesor.pScoreQuestionFive')}
                id="evaluation-profesor-pScoreQuestionFive"
                name="pScoreQuestionFive"
                data-cy="pScoreQuestionFive"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationProfesor.pScoreQuestionSix')}
                id="evaluation-profesor-pScoreQuestionSix"
                name="pScoreQuestionSix"
                data-cy="pScoreQuestionSix"
                type="text"
              />
              <ValidatedField
                id="evaluation-profesor-enrollCourse"
                name="enrollCourse"
                data-cy="enrollCourse"
                label={translate('evaluarCursosApp.evaluationProfesor.enrollCourse')}
                type="select"
              >
                <option value="" key="0" />
                {enrollCourses
                  ? enrollCourses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/evaluation-profesor" replace color="info">
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

export default EvaluationProfesorUpdate;
