import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAcademicPeriod } from 'app/shared/model/academic-period.model';
import { getEntities } from './academic-period.reducer';

export const AcademicPeriod = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const academicPeriodList = useAppSelector(state => state.academicPeriod.entities);
  const loading = useAppSelector(state => state.academicPeriod.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="academic-period-heading" data-cy="AcademicPeriodHeading">
        <Translate contentKey="evaluarCursosApp.academicPeriod.home.title">Academic Periods</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.academicPeriod.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/academic-period/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.academicPeriod.home.createLabel">Create new Academic Period</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {academicPeriodList && academicPeriodList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.academicPeriod.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.academicPeriod.initPeriod">Init Period</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.academicPeriod.finishPeriod">Finish Period</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {academicPeriodList.map((academicPeriod, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/academic-period/${academicPeriod.id}`} color="link" size="sm">
                      {academicPeriod.id}
                    </Button>
                  </td>
                  <td>
                    {academicPeriod.initPeriod ? (
                      <TextFormat type="date" value={academicPeriod.initPeriod} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {academicPeriod.finishPeriod ? (
                      <TextFormat type="date" value={academicPeriod.finishPeriod} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/academic-period/${academicPeriod.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/academic-period/${academicPeriod.id}/edit`}
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
                        to={`/academic-period/${academicPeriod.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.academicPeriod.home.notFound">No Academic Periods found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AcademicPeriod;
