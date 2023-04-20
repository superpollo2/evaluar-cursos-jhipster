import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './evaluation-profesor.reducer';

export const EvaluationProfesorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const evaluationProfesorEntity = useAppSelector(state => state.evaluationProfesor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="evaluationProfesorDetailsHeading">
          <Translate contentKey="evaluarCursosApp.evaluationProfesor.detail.title">EvaluationProfesor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{evaluationProfesorEntity.id}</dd>
          <dt>
            <span id="pScoreQuestionOne">
              <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionOne">P Score Question One</Translate>
            </span>
          </dt>
          <dd>{evaluationProfesorEntity.pScoreQuestionOne}</dd>
          <dt>
            <span id="pScoreQuestionTwo">
              <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionTwo">P Score Question Two</Translate>
            </span>
          </dt>
          <dd>{evaluationProfesorEntity.pScoreQuestionTwo}</dd>
          <dt>
            <span id="pScoreQuestionThree">
              <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionThree">P Score Question Three</Translate>
            </span>
          </dt>
          <dd>{evaluationProfesorEntity.pScoreQuestionThree}</dd>
          <dt>
            <span id="pScoreQuestionFour">
              <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionFour">P Score Question Four</Translate>
            </span>
          </dt>
          <dd>{evaluationProfesorEntity.pScoreQuestionFour}</dd>
          <dt>
            <span id="pScoreQuestionFive">
              <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionFive">P Score Question Five</Translate>
            </span>
          </dt>
          <dd>{evaluationProfesorEntity.pScoreQuestionFive}</dd>
          <dt>
            <span id="pScoreQuestionSix">
              <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionSix">P Score Question Six</Translate>
            </span>
          </dt>
          <dd>{evaluationProfesorEntity.pScoreQuestionSix}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.evaluationProfesor.enrollCourse">Enroll Course</Translate>
          </dt>
          <dd>{evaluationProfesorEntity.enrollCourse ? evaluationProfesorEntity.enrollCourse.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/evaluation-profesor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/evaluation-profesor/${evaluationProfesorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EvaluationProfesorDetail;
