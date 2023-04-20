import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEvaluationCourse } from 'app/shared/model/evaluation-course.model';
import { getEntities } from './evaluation-course.reducer';

export const EvaluationCourse = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const evaluationCourseList = useAppSelector(state => state.evaluationCourse.entities);
  const loading = useAppSelector(state => state.evaluationCourse.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="evaluation-course-heading" data-cy="EvaluationCourseHeading">
        <Translate contentKey="evaluarCursosApp.evaluationCourse.home.title">Evaluation Courses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.evaluationCourse.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/evaluation-course/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.evaluationCourse.home.createLabel">Create new Evaluation Course</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {evaluationCourseList && evaluationCourseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionOne">C Score Question One</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionTwo">C Score Question Two</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionThree">C Score Question Three</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionFour">C Score Question Four</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionFive">C Score Question Five</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.cScoreQuestionSix">C Score Question Six</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.evaluationCourse.enrollCourse">Enroll Course</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {evaluationCourseList.map((evaluationCourse, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/evaluation-course/${evaluationCourse.id}`} color="link" size="sm">
                      {evaluationCourse.id}
                    </Button>
                  </td>
                  <td>{evaluationCourse.cScoreQuestionOne}</td>
                  <td>{evaluationCourse.cScoreQuestionTwo}</td>
                  <td>{evaluationCourse.cScoreQuestionThree}</td>
                  <td>{evaluationCourse.cScoreQuestionFour}</td>
                  <td>{evaluationCourse.cScoreQuestionFive}</td>
                  <td>{evaluationCourse.cScoreQuestionSix}</td>
                  <td>
                    {evaluationCourse.enrollCourse ? (
                      <Link to={`/enroll-course/${evaluationCourse.enrollCourse.id}`}>{evaluationCourse.enrollCourse.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/evaluation-course/${evaluationCourse.id}`}
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
                        to={`/evaluation-course/${evaluationCourse.id}/edit`}
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
                        to={`/evaluation-course/${evaluationCourse.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.evaluationCourse.home.notFound">No Evaluation Courses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EvaluationCourse;
