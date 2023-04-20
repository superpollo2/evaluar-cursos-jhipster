import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './total-score-profesor.reducer';

export const TotalScoreProfesorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const totalScoreProfesorEntity = useAppSelector(state => state.totalScoreProfesor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="totalScoreProfesorDetailsHeading">
          <Translate contentKey="evaluarCursosApp.totalScoreProfesor.detail.title">TotalScoreProfesor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{totalScoreProfesorEntity.id}</dd>
          <dt>
            <span id="pAverageOne">
              <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageOne">P Average One</Translate>
            </span>
          </dt>
          <dd>{totalScoreProfesorEntity.pAverageOne}</dd>
          <dt>
            <span id="pAverageTwo">
              <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageTwo">P Average Two</Translate>
            </span>
          </dt>
          <dd>{totalScoreProfesorEntity.pAverageTwo}</dd>
          <dt>
            <span id="pAverageThree">
              <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageThree">P Average Three</Translate>
            </span>
          </dt>
          <dd>{totalScoreProfesorEntity.pAverageThree}</dd>
          <dt>
            <span id="pAverageFour">
              <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageFour">P Average Four</Translate>
            </span>
          </dt>
          <dd>{totalScoreProfesorEntity.pAverageFour}</dd>
          <dt>
            <span id="pAverageFive">
              <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageFive">P Average Five</Translate>
            </span>
          </dt>
          <dd>{totalScoreProfesorEntity.pAverageFive}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.totalScoreProfesor.academicPeriod">Academic Period</Translate>
          </dt>
          <dd>{totalScoreProfesorEntity.academicPeriod ? totalScoreProfesorEntity.academicPeriod.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/total-score-profesor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/total-score-profesor/${totalScoreProfesorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TotalScoreProfesorDetail;
