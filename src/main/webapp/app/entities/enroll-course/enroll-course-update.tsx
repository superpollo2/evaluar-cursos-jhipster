import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { IAcademicProgram } from 'app/shared/model/academic-program.model';
import { getEntities as getAcademicPrograms } from 'app/entities/academic-program/academic-program.reducer';
import { IProfesor } from 'app/shared/model/profesor.model';
import { getEntities as getProfesors } from 'app/entities/profesor/profesor.reducer';
import { IAcademicPeriod } from 'app/shared/model/academic-period.model';
import { getEntities as getAcademicPeriods } from 'app/entities/academic-period/academic-period.reducer';
import { IEnrollCourse } from 'app/shared/model/enroll-course.model';
import { getEntity, updateEntity, createEntity, reset } from './enroll-course.reducer';

export const EnrollCourseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const students = useAppSelector(state => state.student.entities);
  const academicPrograms = useAppSelector(state => state.academicProgram.entities);
  const profesors = useAppSelector(state => state.profesor.entities);
  const academicPeriods = useAppSelector(state => state.academicPeriod.entities);
  const enrollCourseEntity = useAppSelector(state => state.enrollCourse.entity);
  const loading = useAppSelector(state => state.enrollCourse.loading);
  const updating = useAppSelector(state => state.enrollCourse.updating);
  const updateSuccess = useAppSelector(state => state.enrollCourse.updateSuccess);

  const handleClose = () => {
    navigate('/enroll-course');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStudents({}));
    dispatch(getAcademicPrograms({}));
    dispatch(getProfesors({}));
    dispatch(getAcademicPeriods({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...enrollCourseEntity,
      ...values,
      student: students.find(it => it.id.toString() === values.student.toString()),
      academicProgram: academicPrograms.find(it => it.id.toString() === values.academicProgram.toString()),
      profesor: profesors.find(it => it.id.toString() === values.profesor.toString()),
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
          ...enrollCourseEntity,
          student: enrollCourseEntity?.student?.id,
          academicProgram: enrollCourseEntity?.academicProgram?.id,
          profesor: enrollCourseEntity?.profesor?.id,
          academicPeriod: enrollCourseEntity?.academicPeriod?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.enrollCourse.home.createOrEditLabel" data-cy="EnrollCourseCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.enrollCourse.home.createOrEditLabel">Create or edit a EnrollCourse</Translate>
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
                  id="enroll-course-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.enrollCourse.periodAcademic')}
                id="enroll-course-periodAcademic"
                name="periodAcademic"
                data-cy="periodAcademic"
                type="text"
              />
              <ValidatedField
                label={translate('evaluarCursosApp.enrollCourse.isEvaluated')}
                id="enroll-course-isEvaluated"
                name="isEvaluated"
                data-cy="isEvaluated"
                type="text"
              />
              <ValidatedField
                id="enroll-course-student"
                name="student"
                data-cy="student"
                label={translate('evaluarCursosApp.enrollCourse.student')}
                type="select"
              >
                <option value="" key="0" />
                {students
                  ? students.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="enroll-course-academicProgram"
                name="academicProgram"
                data-cy="academicProgram"
                label={translate('evaluarCursosApp.enrollCourse.academicProgram')}
                type="select"
              >
                <option value="" key="0" />
                {academicPrograms
                  ? academicPrograms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="enroll-course-profesor"
                name="profesor"
                data-cy="profesor"
                label={translate('evaluarCursosApp.enrollCourse.profesor')}
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
              <ValidatedField
                id="enroll-course-academicPeriod"
                name="academicPeriod"
                data-cy="academicPeriod"
                label={translate('evaluarCursosApp.enrollCourse.academicPeriod')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/enroll-course" replace color="info">
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

export default EnrollCourseUpdate;
