import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfesor } from 'app/shared/model/profesor.model';
import { getEntities } from './profesor.reducer';

export const Profesor = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const profesorList = useAppSelector(state => state.profesor.entities);
  const loading = useAppSelector(state => state.profesor.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="profesor-heading" data-cy="ProfesorHeading">
        <Translate contentKey="evaluarCursosApp.profesor.home.title">Profesors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.profesor.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/profesor/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.profesor.home.createLabel">Create new Profesor</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {profesorList && profesorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.profesor.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.profesor.profesorName">Profesor Name</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.profesor.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {profesorList.map((profesor, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/profesor/${profesor.id}`} color="link" size="sm">
                      {profesor.id}
                    </Button>
                  </td>
                  <td>{profesor.profesorName}</td>
                  <td>{profesor.user ? <Link to={`/user-o/${profesor.user.userName}`}>{profesor.user.userName}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/profesor/${profesor.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/profesor/${profesor.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/profesor/${profesor.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="evaluarCursosApp.profesor.home.notFound">No Profesors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Profesor;
