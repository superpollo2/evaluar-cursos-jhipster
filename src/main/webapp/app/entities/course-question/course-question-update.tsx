import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICourseQuestion } from 'app/shared/model/course-question.model';
import { getEntity, updateEntity, createEntity, reset } from './course-question.reducer';

export const CourseQuestionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const courseQuestionEntity = useAppSelector(state => state.courseQuestion.entity);
  const loading = useAppSelector(state => state.courseQuestion.loading);
  const updating = useAppSelector(state => state.courseQuestion.updating);
  const updateSuccess = useAppSelector(state => state.courseQuestion.updateSuccess);

  const handleClose = () => {
    navigate('/course-question');
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
    const entity = {
      ...courseQuestionEntity,
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
      ? {}
      : {
          ...courseQuestionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.courseQuestion.home.createOrEditLabel" data-cy="CourseQuestionCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.courseQuestion.home.createOrEditLabel">Create or edit a CourseQuestion</Translate>
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
                  id="course-question-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.courseQuestion.questionId')}
                id="course-question-questionId"
                name="questionId"
                data-cy="questionId"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.courseQuestion.courseQuestion')}
                id="course-question-courseQuestion"
                name="courseQuestion"
                data-cy="courseQuestion"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/course-question" replace color="info">
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

export default CourseQuestionUpdate;
