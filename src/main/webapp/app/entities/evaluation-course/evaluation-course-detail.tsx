import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './evaluation-course.reducer';

export const EvaluationCourseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const evaluationCourseEntity = useAppSelector(state => state.evaluationCourse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="evaluationCourseDetailsHeading">
          <Translate contentKey="evaluarCursosApp.evaluationCourse.detail.title">EvaluationCourse</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{evaluationCourseEntity.id}</dd>
          <dt>
            <span id="cScoreQuestionOne">
              <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionOne">C Score Question One</Translate>
            </span>
          </dt>
          <dd>{evaluationCourseEntity.cScoreQuestionOne}</dd>
          <dt>
            <span id="cScoreQuestionTwo">
              <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionTwo">C Score Question Two</Translate>
            </span>
          </dt>
          <dd>{evaluationCourseEntity.cScoreQuestionTwo}</dd>
          <dt>
            <span id="cScoreQuestionThree">
              <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionThree">C Score Question Three</Translate>
            </span>
          </dt>
          <dd>{evaluationCourseEntity.cScoreQuestionThree}</dd>
          <dt>
            <span id="cScoreQuestionFour">
              <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionFour">C Score Question Four</Translate>
            </span>
          </dt>
          <dd>{evaluationCourseEntity.cScoreQuestionFour}</dd>
          <dt>
            <span id="cScoreQuestionFive">
              <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionFive">C Score Question Five</Translate>
            </span>
          </dt>
          <dd>{evaluationCourseEntity.cScoreQuestionFive}</dd>
          <dt>
            <span id="cScoreQuestionSix">
              <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionSix">C Score Question Six</Translate>
            </span>
          </dt>
          <dd>{evaluationCourseEntity.cScoreQuestionSix}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.evaluationCourse.enrollCourse">Enroll Course</Translate>
          </dt>
          <dd>{evaluationCourseEntity.enrollCourse ? evaluationCourseEntity.enrollCourse.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/evaluation-course" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/evaluation-course/${evaluationCourseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EvaluationCourseDetail;
