import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEnrollCourse } from 'app/shared/model/enroll-course.model';
import { getEntities } from './enroll-course.reducer';

export const EnrollCourse = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const enrollCourseList = useAppSelector(state => state.enrollCourse.entities);
  const loading = useAppSelector(state => state.enrollCourse.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="enroll-course-heading" data-cy="EnrollCourseHeading">
        <Translate contentKey="evaluarCursosApp.enrollCourse.home.title">Enroll Courses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.enrollCourse.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/enroll-course/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.enrollCourse.home.createLabel">Create new Enroll Course</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {enrollCourseList && enrollCourseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.enrollCourse.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.enrollCourse.periodAcademic">Period Academic</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.enrollCourse.isEvaluated">Is Evaluated</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.enrollCourse.student">Student</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.enrollCourse.academicProgram">Academic Program</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.enrollCourse.profesor">Profesor</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.enrollCourse.academicPeriod">Academic Period</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {enrollCourseList.map((enrollCourse, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/enroll-course/${enrollCourse.id}`} color="link" size="sm">
                      {enrollCourse.id}
                    </Button>
                  </td>
                  <td>{enrollCourse.periodAcademic}</td>
                  <td>{enrollCourse.isEvaluated}</td>
                  <td>{enrollCourse.student ? <Link to={`/student/${enrollCourse.student.id}`}>{enrollCourse.student.id}</Link> : ''}</td>
                  <td>
                    {enrollCourse.academicProgram ? (
                      <Link to={`/academic-program/${enrollCourse.academicProgram.id}`}>{enrollCourse.academicProgram.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {enrollCourse.profesor ? <Link to={`/profesor/${enrollCourse.profesor.id}`}>{enrollCourse.profesor.id}</Link> : ''}
                  </td>
                  <td>
                    {enrollCourse.academicPeriod ? (
                      <Link to={`/academic-period/${enrollCourse.academicPeriod.id}`}>{enrollCourse.academicPeriod.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/enroll-course/${enrollCourse.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/enroll-course/${enrollCourse.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/enroll-course/${enrollCourse.id}/delete`}
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
              <Translate contentKey="evaluarCursosApp.enrollCourse.home.notFound">No Enroll Courses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EnrollCourse;
