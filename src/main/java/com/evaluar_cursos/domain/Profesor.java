package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Profesor.
 */
@Entity
@Table(name = "profesor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "profesor_name")
    private String profesorName;

    @JsonIgnoreProperties(value = { "role" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private UserO user;

    @OneToMany(mappedBy = "profesor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "academicProgram", "profesor", "academicPeriod" }, allowSetters = true)
    private Set<EnrollCourse> enrollCourses = new HashSet<>();

    @OneToMany(mappedBy = "profesor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "academicPeriod", "course", "profesor" }, allowSetters = true)
    private Set<TotalScoreCourse> totalScoreCourses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profesor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesorName() {
        return this.profesorName;
    }

    public Profesor profesorName(String profesorName) {
        this.setProfesorName(profesorName);
        return this;
    }

    public void setProfesorName(String profesorName) {
        this.profesorName = profesorName;
    }

    public UserO getUser() {
        return this.user;
    }

    public void setUser(UserO userO) {
        this.user = userO;
    }

    public Profesor user(UserO userO) {
        this.setUser(userO);
        return this;
    }

    public Set<EnrollCourse> getEnrollCourses() {
        return this.enrollCourses;
    }

    public void setEnrollCourses(Set<EnrollCourse> enrollCourses) {
        if (this.enrollCourses != null) {
            this.enrollCourses.forEach(i -> i.setProfesor(null));
        }
        if (enrollCourses != null) {
            enrollCourses.forEach(i -> i.setProfesor(this));
        }
        this.enrollCourses = enrollCourses;
    }

    public Profesor enrollCourses(Set<EnrollCourse> enrollCourses) {
        this.setEnrollCourses(enrollCourses);
        return this;
    }

    public Profesor addEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.add(enrollCourse);
        enrollCourse.setProfesor(this);
        return this;
    }

    public Profesor removeEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.remove(enrollCourse);
        enrollCourse.setProfesor(null);
        return this;
    }

    public Set<TotalScoreCourse> getTotalScoreCourses() {
        return this.totalScoreCourses;
    }

    public void setTotalScoreCourses(Set<TotalScoreCourse> totalScoreCourses) {
        if (this.totalScoreCourses != null) {
            this.totalScoreCourses.forEach(i -> i.setProfesor(null));
        }
        if (totalScoreCourses != null) {
            totalScoreCourses.forEach(i -> i.setProfesor(this));
        }
        this.totalScoreCourses = totalScoreCourses;
    }

    public Profesor totalScoreCourses(Set<TotalScoreCourse> totalScoreCourses) {
        this.setTotalScoreCourses(totalScoreCourses);
        return this;
    }

    public Profesor addTotalScoreCourse(TotalScoreCourse totalScoreCourse) {
        this.totalScoreCourses.add(totalScoreCourse);
        totalScoreCourse.setProfesor(this);
        return this;
    }

    public Profesor removeTotalScoreCourse(TotalScoreCourse totalScoreCourse) {
        this.totalScoreCourses.remove(totalScoreCourse);
        totalScoreCourse.setProfesor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profesor)) {
            return false;
        }
        return id != null && id.equals(((Profesor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profesor{" +
            "id=" + getId() +
            ", profesorName='" + getProfesorName() + "'" +
            "}";
    }
}
