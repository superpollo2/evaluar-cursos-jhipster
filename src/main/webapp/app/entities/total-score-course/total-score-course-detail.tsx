import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './total-score-course.reducer';

export const TotalScoreCourseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const totalScoreCourseEntity = useAppSelector(state => state.totalScoreCourse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="totalScoreCourseDetailsHeading">
          <Translate contentKey="evaluarCursosApp.totalScoreCourse.detail.title">TotalScoreCourse</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{totalScoreCourseEntity.id}</dd>
          <dt>
            <span id="cAverageOne">
              <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageOne">C Average One</Translate>
            </span>
          </dt>
          <dd>{totalScoreCourseEntity.cAverageOne}</dd>
          <dt>
            <span id="cAverageTwo">
              <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageTwo">C Average Two</Translate>
            </span>
          </dt>
          <dd>{totalScoreCourseEntity.cAverageTwo}</dd>
          <dt>
            <span id="cAverageThree">
              <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageThree">C Average Three</Translate>
            </span>
          </dt>
          <dd>{totalScoreCourseEntity.cAverageThree}</dd>
          <dt>
            <span id="cAverageFour">
              <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageFour">C Average Four</Translate>
            </span>
          </dt>
          <dd>{totalScoreCourseEntity.cAverageFour}</dd>
          <dt>
            <span id="cAverageFive">
              <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageFive">C Average Five</Translate>
            </span>
          </dt>
          <dd>{totalScoreCourseEntity.cAverageFive}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.totalScoreCourse.academicPeriod">Academic Period</Translate>
          </dt>
          <dd>{totalScoreCourseEntity.academicPeriod ? totalScoreCourseEntity.academicPeriod.id : ''}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.totalScoreCourse.course">Course</Translate>
          </dt>
          <dd>{totalScoreCourseEntity.course ? totalScoreCourseEntity.course.id : ''}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.totalScoreCourse.profesor">Profesor</Translate>
          </dt>
          <dd>{totalScoreCourseEntity.profesor ? totalScoreCourseEntity.profesor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/total-score-course" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/total-score-course/${totalScoreCourseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TotalScoreCourseDetail;
