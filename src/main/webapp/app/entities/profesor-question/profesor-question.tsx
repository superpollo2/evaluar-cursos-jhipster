import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfesorQuestion } from 'app/shared/model/profesor-question.model';
import { getEntities } from './profesor-question.reducer';

export const ProfesorQuestion = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const profesorQuestionList = useAppSelector(state => state.profesorQuestion.entities);
  const loading = useAppSelector(state => state.profesorQuestion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="profesor-question-heading" data-cy="ProfesorQuestionHeading">
        <Translate contentKey="evaluarCursosApp.profesorQuestion.home.title">Profesor Questions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.profesorQuestion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/profesor-question/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.profesorQuestion.home.createLabel">Create new Profesor Question</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {profesorQuestionList && profesorQuestionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.profesorQuestion.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.profesorQuestion.questionId">Question Id</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.profesorQuestion.profesorQuestion">Profesor Question</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {profesorQuestionList.map((profesorQuestion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/profesor-question/${profesorQuestion.id}`} color="link" size="sm">
                      {profesorQuestion.id}
                    </Button>
                  </td>
                  <td>{profesorQuestion.questionId}</td>
                  <td>{profesorQuestion.profesorQuestion}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/profesor-question/${profesorQuestion.id}`}
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
                        to={`/profesor-question/${profesorQuestion.id}/edit`}
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
                        to={`/profesor-question/${profesorQuestion.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.profesorQuestion.home.notFound">No Profesor Questions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ProfesorQuestion;
