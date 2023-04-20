import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITotalScoreCourse } from 'app/shared/model/total-score-course.model';
import { getEntities } from './total-score-course.reducer';

export const TotalScoreCourse = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const totalScoreCourseList = useAppSelector(state => state.totalScoreCourse.entities);
  const loading = useAppSelector(state => state.totalScoreCourse.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="total-score-course-heading" data-cy="TotalScoreCourseHeading">
        <Translate contentKey="evaluarCursosApp.totalScoreCourse.home.title">Total Score Courses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.totalScoreCourse.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/total-score-course/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.totalScoreCourse.home.createLabel">Create new Total Score Course</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {totalScoreCourseList && totalScoreCourseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageOne">C Average One</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageTwo">C Average Two</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageThree">C Average Three</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageFour">C Average Four</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.cAverageFive">C Average Five</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.academicPeriod">Academic Period</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.course">Course</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.totalScoreCourse.profesor">Profesor</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {totalScoreCourseList.map((totalScoreCourse, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/total-score-course/${totalScoreCourse.id}`} color="link" size="sm">
                      {totalScoreCourse.id}
                    </Button>
                  </td>
                  <td>{totalScoreCourse.cAverageOne}</td>
                  <td>{totalScoreCourse.cAverageTwo}</td>
                  <td>{totalScoreCourse.cAverageThree}</td>
                  <td>{totalScoreCourse.cAverageFour}</td>
                  <td>{totalScoreCourse.cAverageFive}</td>
                  <td>
                    {totalScoreCourse.academicPeriod ? (
                      <Link to={`/academic-period/${totalScoreCourse.academicPeriod.id}`}>{totalScoreCourse.academicPeriod.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {totalScoreCourse.course ? <Link to={`/course/${totalScoreCourse.course.id}`}>{totalScoreCourse.course.id}</Link> : ''}
                  </td>
                  <td>
                    {totalScoreCourse.profesor ? (
                      <Link to={`/profesor/${totalScoreCourse.profesor.id}`}>{totalScoreCourse.profesor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/total-score-course/${totalScoreCourse.id}`}
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
                        to={`/total-score-course/${totalScoreCourse.id}/edit`}
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
                        to={`/total-score-course/${totalScoreCourse.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.totalScoreCourse.home.notFound">No Total Score Courses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TotalScoreCourse;
