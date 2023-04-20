package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AcademicProgram.
 */
@Entity
@Table(name = "academic_program")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcademicProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "program_name")
    private String programName;

    @OneToMany(mappedBy = "program")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "enrollCourses", "program" }, allowSetters = true)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "academicProgram")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "academicProgram", "profesor", "academicPeriod" }, allowSetters = true)
    private Set<EnrollCourse> enrollCourses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AcademicProgram id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramName() {
        return this.programName;
    }

    public AcademicProgram programName(String programName) {
        this.setProgramName(programName);
        return this;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        if (this.students != null) {
            this.students.forEach(i -> i.setProgram(null));
        }
        if (students != null) {
            students.forEach(i -> i.setProgram(this));
        }
        this.students = students;
    }

    public AcademicProgram students(Set<Student> students) {
        this.setStudents(students);
        return this;
    }

    public AcademicProgram addStudent(Student student) {
        this.students.add(student);
        student.setProgram(this);
        return this;
    }

    public AcademicProgram removeStudent(Student student) {
        this.students.remove(student);
        student.setProgram(null);
        return this;
    }

    public Set<EnrollCourse> getEnrollCourses() {
        return this.enrollCourses;
    }

    public void setEnrollCourses(Set<EnrollCourse> enrollCourses) {
        if (this.enrollCourses != null) {
            this.enrollCourses.forEach(i -> i.setAcademicProgram(null));
        }
        if (enrollCourses != null) {
            enrollCourses.forEach(i -> i.setAcademicProgram(this));
        }
        this.enrollCourses = enrollCourses;
    }

    public AcademicProgram enrollCourses(Set<EnrollCourse> enrollCourses) {
        this.setEnrollCourses(enrollCourses);
        return this;
    }

    public AcademicProgram addEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.add(enrollCourse);
        enrollCourse.setAcademicProgram(this);
        return this;
    }

    public AcademicProgram removeEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.remove(enrollCourse);
        enrollCourse.setAcademicProgram(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicProgram)) {
            return false;
        }
        return id != null && id.equals(((AcademicProgram) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicProgram{" +
            "id=" + getId() +
            ", programName='" + getProgramName() + "'" +
            "}";
    }
}
