import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './enroll-course.reducer';

export const EnrollCourseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const enrollCourseEntity = useAppSelector(state => state.enrollCourse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enrollCourseDetailsHeading">
          <Translate contentKey="evaluarCursosApp.enrollCourse.detail.title">EnrollCourse</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{enrollCourseEntity.id}</dd>
          <dt>
            <span id="enrollId">
              <Translate contentKey="evaluarCursosApp.enrollCourse.enrollId">Enroll Id</Translate>
            </span>
          </dt>
          <dd>{enrollCourseEntity.enrollId}</dd>
          <dt>
            <span id="periodAcademic">
              <Translate contentKey="evaluarCursosApp.enrollCourse.periodAcademic">Period Academic</Translate>
            </span>
          </dt>
          <dd>{enrollCourseEntity.periodAcademic}</dd>
          <dt>
            <span id="isEvaluated">
              <Translate contentKey="evaluarCursosApp.enrollCourse.isEvaluated">Is Evaluated</Translate>
            </span>
          </dt>
          <dd>{enrollCourseEntity.isEvaluated}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.enrollCourse.student">Student</Translate>
          </dt>
          <dd>{enrollCourseEntity.student ? enrollCourseEntity.student.id : ''}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.enrollCourse.academicProgram">Academic Program</Translate>
          </dt>
          <dd>{enrollCourseEntity.academicProgram ? enrollCourseEntity.academicProgram.id : ''}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.enrollCourse.profesor">Profesor</Translate>
          </dt>
          <dd>{enrollCourseEntity.profesor ? enrollCourseEntity.profesor.id : ''}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.enrollCourse.academicPeriod">Academic Period</Translate>
          </dt>
          <dd>{enrollCourseEntity.academicPeriod ? enrollCourseEntity.academicPeriod.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/enroll-course" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enroll-course/${enrollCourseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnrollCourseDetail;
