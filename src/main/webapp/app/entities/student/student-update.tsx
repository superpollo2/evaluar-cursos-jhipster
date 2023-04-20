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
import { IAcademicProgram } from 'app/shared/model/academic-program.model';
import { getEntities as getAcademicPrograms } from 'app/entities/academic-program/academic-program.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { getEntity, updateEntity, createEntity, reset } from './student.reducer';

export const StudentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userOS = useAppSelector(state => state.userO.entities);
  const academicPrograms = useAppSelector(state => state.academicProgram.entities);
  const studentEntity = useAppSelector(state => state.student.entity);
  const loading = useAppSelector(state => state.student.loading);
  const updating = useAppSelector(state => state.student.updating);
  const updateSuccess = useAppSelector(state => state.student.updateSuccess);

  const handleClose = () => {
    navigate('/student');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserOs({}));
    dispatch(getAcademicPrograms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...studentEntity,
      ...values,
      user: userOS.find(it => it.userName.toString() === values.user.toString()),
      program: academicPrograms.find(it => it.id.toString() === values.program.toString()),
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
          ...studentEntity,
          user: studentEntity?.user?.userName,
          program: studentEntity?.program?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="evaluarCursosApp.student.home.createOrEditLabel" data-cy="StudentCreateUpdateHeading">
            <Translate contentKey="evaluarCursosApp.student.home.createOrEditLabel">Create or edit a Student</Translate>
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
                  id="student-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('evaluarCursosApp.student.studentName')}
                id="student-studentName"
                name="studentName"
                data-cy="studentName"
                type="text"
              />
              <ValidatedField id="student-user" name="user" data-cy="user" label={translate('evaluarCursosApp.student.user')} type="select">
                <option value="" key="0" />
                {userOS
                  ? userOS.map(otherEntity => (
                      <option value={otherEntity.userName} key={otherEntity.userName}>
                        {otherEntity.userName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="student-program"
                name="program"
                data-cy="program"
                label={translate('evaluarCursosApp.student.program')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/student" replace color="info">
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

export default StudentUpdate;
