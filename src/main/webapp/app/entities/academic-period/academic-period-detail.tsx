import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './academic-period.reducer';

export const AcademicPeriodDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const academicPeriodEntity = useAppSelector(state => state.academicPeriod.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="academicPeriodDetailsHeading">
          <Translate contentKey="evaluarCursosApp.academicPeriod.detail.title">AcademicPeriod</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{academicPeriodEntity.id}</dd>
          <dt>
            <span id="periodId">
              <Translate contentKey="evaluarCursosApp.academicPeriod.periodId">Period Id</Translate>
            </span>
          </dt>
          <dd>{academicPeriodEntity.periodId}</dd>
          <dt>
            <span id="initPeriod">
              <Translate contentKey="evaluarCursosApp.academicPeriod.initPeriod">Init Period</Translate>
            </span>
          </dt>
          <dd>
            {academicPeriodEntity.initPeriod ? (
              <TextFormat value={academicPeriodEntity.initPeriod} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finishPeriod">
              <Translate contentKey="evaluarCursosApp.academicPeriod.finishPeriod">Finish Period</Translate>
            </span>
          </dt>
          <dd>
            {academicPeriodEntity.finishPeriod ? (
              <TextFormat value={academicPeriodEntity.finishPeriod} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/academic-period" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/academic-period/${academicPeriodEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AcademicPeriodDetail;
