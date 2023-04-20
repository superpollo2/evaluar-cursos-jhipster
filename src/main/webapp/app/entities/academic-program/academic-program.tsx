import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAcademicProgram } from 'app/shared/model/academic-program.model';
import { getEntities } from './academic-program.reducer';

export const AcademicProgram = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const academicProgramList = useAppSelector(state => state.academicProgram.entities);
  const loading = useAppSelector(state => state.academicProgram.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="academic-program-heading" data-cy="AcademicProgramHeading">
        <Translate contentKey="evaluarCursosApp.academicProgram.home.title">Academic Programs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.academicProgram.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/academic-program/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.academicProgram.home.createLabel">Create new Academic Program</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {academicProgramList && academicProgramList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.academicProgram.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.academicProgram.programId">Program Id</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.academicProgram.programName">Program Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {academicProgramList.map((academicProgram, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/academic-program/${academicProgram.id}`} color="link" size="sm">
                      {academicProgram.id}
                    </Button>
                  </td>
                  <td>{academicProgram.programId}</td>
                  <td>{academicProgram.programName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/academic-program/${academicProgram.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/academic-program/${academicProgram.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/academic-program/${academicProgram.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="evaluarCursosApp.academicProgram.home.notFound">No Academic Programs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AcademicProgram;
