import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profesor-question.reducer';

export const ProfesorQuestionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profesorQuestionEntity = useAppSelector(state => state.profesorQuestion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profesorQuestionDetailsHeading">
          <Translate contentKey="evaluarCursosApp.profesorQuestion.detail.title">ProfesorQuestion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profesorQuestionEntity.id}</dd>
          <dt>
            <span id="questionId">
              <Translate contentKey="evaluarCursosApp.profesorQuestion.questionId">Question Id</Translate>
            </span>
          </dt>
          <dd>{profesorQuestionEntity.questionId}</dd>
          <dt>
            <span id="profesorQuestion">
              <Translate contentKey="evaluarCursosApp.profesorQuestion.profesorQuestion">Profesor Question</Translate>
            </span>
          </dt>
          <dd>{profesorQuestionEntity.profesorQuestion}</dd>
        </dl>
        <Button tag={Link} to="/profesor-question" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profesor-question/${profesorQuestionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfesorQuestionDetail;
