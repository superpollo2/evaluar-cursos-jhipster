import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITotalScoreProfesor } from 'app/shared/model/total-score-profesor.model';
import { getEntities } from './total-score-profesor.reducer';

export const TotalScoreProfesor = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const totalScoreProfesorList = useAppSelector(state => state.totalScoreProfesor.entities);
  const loading = useAppSelector(state => state.totalScoreProfesor.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="total-score-profesor-heading" data-cy="TotalScoreProfesorHeading">
        <Translate contentKey="evaluarCursosApp.totalScoreProfesor.home.title">Total Score Profesors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.totalScoreProfesor.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/total-score-profesor/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.totalScoreProfesor.home.createLabel">Create new Total Score Profesor</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {totalScoreProfesorList && totalScoreProfesorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreProfesor.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageOne">P Average One</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageTwo">P Average Two</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageThree">P Average Three</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageFour">P Average Four</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreProfesor.pAverageFive">P Average Five</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreProfesor.academicPeriod">Academic Period</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {totalScoreProfesorList.map((totalScoreProfesor, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/total-score-profesor/${totalScoreProfesor.id}`} color="link" size="sm">
                      {totalScoreProfesor.id}
                    </Button>
                  </td>
                  <td>{totalScoreProfesor.pAverageOne}</td>
                  <td>{totalScoreProfesor.pAverageTwo}</td>
                  <td>{totalScoreProfesor.pAverageThree}</td>
                  <td>{totalScoreProfesor.pAverageFour}</td>
                  <td>{totalScoreProfesor.pAverageFive}</td>
                  <td>
                    {totalScoreProfesor.academicPeriod ? (
                      <Link to={`/academic-period/${totalScoreProfesor.academicPeriod.id}`}>{totalScoreProfesor.academicPeriod.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/total-score-profesor/${totalScoreProfesor.id}`}
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
                        to={`/total-score-profesor/${totalScoreProfesor.id}/edit`}
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
                        to={`/total-score-profesor/${totalScoreProfesor.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.totalScoreProfesor.home.notFound">No Total Score Profesors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TotalScoreProfesor;
