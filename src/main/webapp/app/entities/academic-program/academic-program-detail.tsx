import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './academic-program.reducer';

export const AcademicProgramDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const academicProgramEntity = useAppSelector(state => state.academicProgram.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="academicProgramDetailsHeading">
          <Translate contentKey="evaluarCursosApp.academicProgram.detail.title">AcademicProgram</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{academicProgramEntity.id}</dd>
          <dt>
            <span id="programId">
              <Translate contentKey="evaluarCursosApp.academicProgram.programId">Program Id</Translate>
            </span>
          </dt>
          <dd>{academicProgramEntity.programId}</dd>
          <dt>
            <span id="programName">
              <Translate contentKey="evaluarCursosApp.academicProgram.programName">Program Name</Translate>
            </span>
          </dt>
          <dd>{academicProgramEntity.programName}</dd>
        </dl>
        <Button tag={Link} to="/academic-program" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/academic-program/${academicProgramEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AcademicProgramDetail;
