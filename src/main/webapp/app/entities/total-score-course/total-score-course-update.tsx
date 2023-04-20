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
import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { IProfesor } from 'app/shared/model/profesor.model';
import { getEntities as getProfesors } from 'app/entities/profesor/profesor.reducer';
import { ITotalScoreCourse } from 'app/shared/model/total-score-course.model';
import { getEntity, updateEntity, createEntity, reset } from './total-score-course.reducer';

export const TotalScoreCourseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const academicPeriods = useAppSelector(state => state.academicPeriod.entities);
  const courses = useAppSelector(state => state.course.entities);
  const profesors = useAppSelector(state => state.profesor.entities);
  const totalScoreCourseEntity = useAppSelector(state => state.totalScoreCourse.entity);
  const loading = useAppSelector(state => state.totalScoreCourse.loading);
  const updating = useAppSelector(state => state.totalScoreCourse.updating);
  const updateSuccess = useAppSelector(state => state.totalScoreCourse.updateSuccess);

  const handleClose = () => {
    navigate('/total-score-course');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAcademicPeriods({}));
    dispatch(getCourses({}));
    dispatch(getProfesors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...totalScoreCourseEntity,
      ...values,
      academicPeriod: academicPeriods.find(it => it.id.toString() === values.academicPeriod.toString()),
      course: courses.find(it => it.id.toString() === values.course.toString()),
      profesor: profesors.find(it => it.id.toString() === values.profesor.toString()),
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
          ...totalScoreCourseEntity,
          academicPeriod: totalScoreCourseEntity?.academicPeriod?.id,
          course: totalScoreCourseEntity?.course?.id,
          profesor: totalScoreCourseEntity?.profesor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.totalScoreCourse.home.createOrEditLabel" data-cy="TotalScoreCourseCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.totalScoreCourse.home.createOrEditLabel">Create or edit a TotalScoreCourse</Translate>
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
                  id="total-score-course-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreCourse.cAverageOne')}
                id="total-score-course-cAverageOne"
                name="cAverageOne"
                data-cy="cAverageOne"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreCourse.cAverageTwo')}
                id="total-score-course-cAverageTwo"
                name="cAverageTwo"
                data-cy="cAverageTwo"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreCourse.cAverageThree')}
                id="total-score-course-cAverageThree"
                name="cAverageThree"
                data-cy="cAverageThree"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreCourse.cAverageFour')}
                id="total-score-course-cAverageFour"
                name="cAverageFour"
                data-cy="cAverageFour"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.totalScoreCourse.cAverageFive')}
                id="total-score-course-cAverageFive"
                name="cAverageFive"
                data-cy="cAverageFive"
                type="text"
              />
              <ValidatedField
                id="total-score-course-academicPeriod"
                name="academicPeriod"
                data-cy="academicPeriod"
                label={translate('evaluarCursosApp.totalScoreCourse.academicPeriod')}
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
              <ValidatedField
                id="total-score-course-course"
                name="course"
                data-cy="course"
                label={translate('evaluarCursosApp.totalScoreCourse.course')}
                type="select"
              >
                <option value="" key="0" />
                {courses
                  ? courses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="total-score-course-profesor"
                name="profesor"
                data-cy="profesor"
                label={translate('evaluarCursosApp.totalScoreCourse.profesor')}
                type="select"
              >
                <option value="" key="0" />
                {profesors
                  ? profesors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/total-score-course" replace color="info">
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

export default TotalScoreCourseUpdate;
