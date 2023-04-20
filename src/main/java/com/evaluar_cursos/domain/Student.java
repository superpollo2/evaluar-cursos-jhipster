package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_name")
    private String studentName;

    @JsonIgnoreProperties(value = { "role" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private UserO user;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "academicProgram", "profesor", "academicPeriod" }, allowSetters = true)
    private Set<EnrollCourse> enrollCourses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "students", "enrollCourses" }, allowSetters = true)
    private AcademicProgram program;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public Student studentName(String studentName) {
        this.setStudentName(studentName);
        return this;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public UserO getUser() {
        return this.user;
    }

    public void setUser(UserO userO) {
        this.user = userO;
    }

    public Student user(UserO userO) {
        this.setUser(userO);
        return this;
    }

    public Set<EnrollCourse> getEnrollCourses() {
        return this.enrollCourses;
    }

    public void setEnrollCourses(Set<EnrollCourse> enrollCourses) {
        if (this.enrollCourses != null) {
            this.enrollCourses.forEach(i -> i.setStudent(null));
        }
        if (enrollCourses != null) {
            enrollCourses.forEach(i -> i.setStudent(this));
        }
        this.enrollCourses = enrollCourses;
    }

    public Student enrollCourses(Set<EnrollCourse> enrollCourses) {
        this.setEnrollCourses(enrollCourses);
        return this;
    }

    public Student addEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.add(enrollCourse);
        enrollCourse.setStudent(this);
        return this;
    }

    public Student removeEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.remove(enrollCourse);
        enrollCourse.setStudent(null);
        return this;
    }

    public AcademicProgram getProgram() {
        return this.program;
    }

    public void setProgram(AcademicProgram academicProgram) {
        this.program = academicProgram;
    }

    public Student program(AcademicProgram academicProgram) {
        this.setProgram(academicProgram);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentName='" + getStudentName() + "'" +
            "}";
    }
}
