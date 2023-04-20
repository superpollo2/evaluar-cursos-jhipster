import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profesor.reducer';

export const ProfesorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profesorEntity = useAppSelector(state => state.profesor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profesorDetailsHeading">
          <Translate contentKey="evaluarCursosApp.profesor.detail.title">Profesor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.id}</dd>
          <dt>
            <span id="profesorId">
              <Translate contentKey="evaluarCursosApp.profesor.profesorId">Profesor Id</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.profesorId}</dd>
          <dt>
            <span id="profesorName">
              <Translate contentKey="evaluarCursosApp.profesor.profesorName">Profesor Name</Translate>
            </span>
          </dt>
          <dd>{profesorEntity.profesorName}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.profesor.user">User</Translate>
          </dt>
          <dd>{profesorEntity.user ? profesorEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/profesor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profesor/${profesorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfesorDetail;