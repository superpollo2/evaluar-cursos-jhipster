import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './course-question.reducer';

export const CourseQuestionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const courseQuestionEntity = useAppSelector(state => state.courseQuestion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseQuestionDetailsHeading">
          <Translate contentKey="evaluarCursosApp.courseQuestion.detail.title">CourseQuestion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{courseQuestionEntity.id}</dd>
          <dt>
            <span id="questionId">
              <Translate contentKey="evaluarCursosApp.courseQuestion.questionId">Question Id</Translate>
            </span>
          </dt>
          <dd>{courseQuestionEntity.questionId}</dd>
          <dt>
            <span id="courseQuestion">
              <Translate contentKey="evaluarCursosApp.courseQuestion.courseQuestion">Course Question</Translate>
            </span>
          </dt>
          <dd>{courseQuestionEntity.courseQuestion}</dd>
        </dl>
        <Button tag={Link} to="/course-question" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-question/${courseQuestionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseQuestionDetail;
