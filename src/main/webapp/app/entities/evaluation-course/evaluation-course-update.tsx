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
import { IEvaluationCourse } from 'app/shared/model/evaluation-course.model';
import { getEntity, updateEntity, createEntity, reset } from './evaluation-course.reducer';

export const EvaluationCourseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const enrollCourses = useAppSelector(state => state.enrollCourse.entities);
  const evaluationCourseEntity = useAppSelector(state => state.evaluationCourse.entity);
  const loading = useAppSelector(state => state.evaluationCourse.loading);
  const updating = useAppSelector(state => state.evaluationCourse.updating);
  const updateSuccess = useAppSelector(state => state.evaluationCourse.updateSuccess);

  const handleClose = () => {
    navigate('/evaluation-course');
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
      ...evaluationCourseEntity,
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
          ...evaluationCourseEntity,
          enrollCourse: evaluationCourseEntity?.enrollCourse?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.evaluationCourse.home.createOrEditLabel" data-cy="EvaluationCourseCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.evaluationCourse.home.createOrEditLabel">Create or edit a EvaluationCourse</Translate>
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
                  id="evaluation-course-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationCourse.cScoreQuestionOne')}
                id="evaluation-course-cScoreQuestionOne"
                name="cScoreQuestionOne"
                data-cy="cScoreQuestionOne"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationCourse.cScoreQuestionTwo')}
                id="evaluation-course-cScoreQuestionTwo"
                name="cScoreQuestionTwo"
                data-cy="cScoreQuestionTwo"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationCourse.cScoreQuestionThree')}
                id="evaluation-course-cScoreQuestionThree"
                name="cScoreQuestionThree"
                data-cy="cScoreQuestionThree"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationCourse.cScoreQuestionFour')}
                id="evaluation-course-cScoreQuestionFour"
                name="cScoreQuestionFour"
                data-cy="cScoreQuestionFour"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationCourse.cScoreQuestionFive')}
                id="evaluation-course-cScoreQuestionFive"
                name="cScoreQuestionFive"
                data-cy="cScoreQuestionFive"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.evaluationCourse.cScoreQuestionSix')}
                id="evaluation-course-cScoreQuestionSix"
                name="cScoreQuestionSix"
                data-cy="cScoreQuestionSix"
                type="text"
              />
              <ValidatedField
                id="evaluation-course-enrollCourse"
                name="enrollCourse"
                data-cy="enrollCourse"
                label={translate('evaluarCursosApp.evaluationCourse.enrollCourse')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/evaluation-course" replace color="info">
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

export default EvaluationCourseUpdate;
