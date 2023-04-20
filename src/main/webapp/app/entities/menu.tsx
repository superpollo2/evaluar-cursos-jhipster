import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/user-o">
        <Translate contentKey="global.menu.entities.userO" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/student">
        <Translate contentKey="global.menu.entities.student" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/academic-program">
        <Translate contentKey="global.menu.entities.academicProgram" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/profesor">
        <Translate contentKey="global.menu.entities.profesor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/enroll-course">
        <Translate contentKey="global.menu.entities.enrollCourse" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/role">
        <Translate contentKey="global.menu.entities.role" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/course">
        <Translate contentKey="global.menu.entities.course" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/course-question">
        <Translate contentKey="global.menu.entities.courseQuestion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/profesor-question">
        <Translate contentKey="global.menu.entities.profesorQuestion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/academic-period">
        <Translate contentKey="global.menu.entities.academicPeriod" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/evaluation-course">
        <Translate contentKey="global.menu.entities.evaluationCourse" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/evaluation-profesor">
        <Translate contentKey="global.menu.entities.evaluationProfesor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/total-score-course">
        <Translate contentKey="global.menu.entities.totalScoreCourse" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/total-score-profesor">
        <Translate contentKey="global.menu.entities.totalScoreProfesor" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
