import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICourseQuestion } from 'app/shared/model/course-question.model';
import { getEntities } from './course-question.reducer';

export const CourseQuestion = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const courseQuestionList = useAppSelector(state => state.courseQuestion.entities);
  const loading = useAppSelector(state => state.courseQuestion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="course-question-heading" data-cy="CourseQuestionHeading">
        <Translate contentKey="evaluarCursosApp.courseQuestion.home.title">Course Questions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.courseQuestion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/course-question/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.courseQuestion.home.createLabel">Create new Course Question</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {courseQuestionList && courseQuestionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.courseQuestion.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.courseQuestion.questionId">Question Id</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.courseQuestion.courseQuestion">Course Question</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {courseQuestionList.map((courseQuestion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/course-question/${courseQuestion.id}`} color="link" size="sm">
                      {courseQuestion.id}
                    </Button>
                  </td>
                  <td>{courseQuestion.questionId}</td>
                  <td>{courseQuestion.courseQuestion}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/course-question/${courseQuestion.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/course-question/${courseQuestion.id}/edit`}
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
                        to={`/course-question/${courseQuestion.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.courseQuestion.home.notFound">No Course Questions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CourseQuestion;
