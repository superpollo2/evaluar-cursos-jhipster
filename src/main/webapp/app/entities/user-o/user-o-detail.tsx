import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-o.reducer';

export const UserODetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userOEntity = useAppSelector(state => state.userO.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userODetailsHeading">
          <Translate contentKey="evaluarCursosApp.userO.detail.title">UserO</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="userName">
              <Translate contentKey="evaluarCursosApp.userO.userName">User Name</Translate>
            </span>
          </dt>
          <dd>{userOEntity.userName}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="evaluarCursosApp.userO.password">Password</Translate>
            </span>
          </dt>
          <dd>{userOEntity.password}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="evaluarCursosApp.userO.email">Email</Translate>
            </span>
          </dt>
          <dd>{userOEntity.email}</dd>
          <dt>
            <Translate contentKey="evaluarCursosApp.userO.role">Role</Translate>
          </dt>
          <dd>{userOEntity.role ? userOEntity.role.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-o" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-o/${userOEntity.userName}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserODetail;
