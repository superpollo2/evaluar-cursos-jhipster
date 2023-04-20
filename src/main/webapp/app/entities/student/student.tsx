import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities } from './student.reducer';

export const Student = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const studentList = useAppSelector(state => state.student.entities);
  const loading = useAppSelector(state => state.student.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="student-heading" data-cy="StudentHeading">
        <Translate contentKey="evaluarCursosApp.student.home.title">Students</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="evaluarCursosApp.student.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/student/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluarCursosApp.student.home.createLabel">Create new Student</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {studentList && studentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="evaluarCursosApp.student.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.student.studentId">Student Id</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.student.studentName">Student Name</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.student.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="evaluarCursosApp.student.program">Program</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {studentList.map((student, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/student/${student.id}`} color="link" size="sm">
                      {student.id}
                    </Button>
                  </td>
                  <td>{student.studentId}</td>
                  <td>{student.studentName}</td>
                  <td>{student.user ? <Link to={`/user-o/${student.user.id}`}>{student.user.id}</Link> : ''}</td>
                  <td>{student.program ? <Link to={`/academic-program/${student.program.id}`}>{student.program.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/student/${student.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/student/${student.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/student/${student.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="evaluarCursosApp.student.home.notFound">No Students found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Student;
