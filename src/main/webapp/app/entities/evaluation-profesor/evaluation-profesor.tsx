import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEvaluationProfesor } from 'app/shared/model/evaluation-profesor.model';
import { getEntities } from './evaluation-profesor.reducer';

export const EvaluationProfesor = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const evaluationProfesorList = useAppSelector(state => state.evaluationProfesor.entities);
  const loading = useAppSelector(state => state.evaluationProfesor.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="evaluation-profesor-heading" data-cy="EvaluationProfesorHeading">
        <Translate contentKey="evaluarCursosApp.evaluationProfesor.home.title">Evaluation Profesors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.evaluationProfesor.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/evaluation-profesor/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.evaluationProfesor.home.createLabel">Create new Evaluation Profesor</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {evaluationProfesorList && evaluationProfesorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionOne">P Score Question One</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionTwo">P Score Question Two</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionThree">P Score Question Three</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionFour">P Score Question Four</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionFive">P Score Question Five</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.pScoreQuestionSix">P Score Question Six</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationProfesor.enrollCourse">Enroll Course</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {evaluationProfesorList.map((evaluationProfesor, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/evaluation-profesor/${evaluationProfesor.id}`} color="link" size="sm">
                      {evaluationProfesor.id}
                    </Button>
                  </td>
                  <td>{evaluationProfesor.pScoreQuestionOne}</td>
                  <td>{evaluationProfesor.pScoreQuestionTwo}</td>
                  <td>{evaluationProfesor.pScoreQuestionThree}</td>
                  <td>{evaluationProfesor.pScoreQuestionFour}</td>
                  <td>{evaluationProfesor.pScoreQuestionFive}</td>
                  <td>{evaluationProfesor.pScoreQuestionSix}</td>
                  <td>
                    {evaluationProfesor.enrollCourse ? (
                      <Link to={`/enroll-course/${evaluationProfesor.enrollCourse.id}`}>{evaluationProfesor.enrollCourse.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/evaluation-profesor/${evaluationProfesor.id}`}
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
                        to={`/evaluation-profesor/${evaluationProfesor.id}/edit`}
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
                        to={`/evaluation-profesor/${evaluationProfesor.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.evaluationProfesor.home.notFound">No Evaluation Profesors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EvaluationProfesor;
