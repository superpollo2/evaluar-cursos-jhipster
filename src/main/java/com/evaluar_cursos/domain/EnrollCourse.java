package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EnrollCourse.
 */
@Entity
@Table(name = "enroll_course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnrollCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "period_academic")
    private String periodAcademic;

    @Column(name = "is_evaluated")
    private Integer isEvaluated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "enrollCourses", "program" }, allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(value = { "students", "enrollCourses" }, allowSetters = true)
    private AcademicProgram academicProgram;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "enrollCourses", "totalScoreCourses" }, allowSetters = true)
    private Profesor profesor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enrollCourses", "totalScoreCourses", "totalScoreProfesors" }, allowSetters = true)
    private AcademicPeriod academicPeriod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EnrollCourse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodAcademic() {
        return this.periodAcademic;
    }

    public EnrollCourse periodAcademic(String periodAcademic) {
        this.setPeriodAcademic(periodAcademic);
        return this;
    }

    public void setPeriodAcademic(String periodAcademic) {
        this.periodAcademic = periodAcademic;
    }

    public Integer getIsEvaluated() {
        return this.isEvaluated;
    }

    public EnrollCourse isEvaluated(Integer isEvaluated) {
        this.setIsEvaluated(isEvaluated);
        return this;
    }

    public void setIsEvaluated(Integer isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public EnrollCourse student(Student student) {
        this.setStudent(student);
        return this;
    }

    public AcademicProgram getAcademicProgram() {
        return this.academicProgram;
    }

    public void setAcademicProgram(AcademicProgram academicProgram) {
        this.academicProgram = academicProgram;
    }

    public EnrollCourse academicProgram(AcademicProgram academicProgram) {
        this.setAcademicProgram(academicProgram);
        return this;
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public EnrollCourse profesor(Profesor profesor) {
        this.setProfesor(profesor);
        return this;
    }

    public AcademicPeriod getAcademicPeriod() {
        return this.academicPeriod;
    }

    public void setAcademicPeriod(AcademicPeriod academicPeriod) {
        this.academicPeriod = academicPeriod;
    }

    public EnrollCourse academicPeriod(AcademicPeriod academicPeriod) {
        this.setAcademicPeriod(academicPeriod);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnrollCourse)) {
            return false;
        }
        return id != null && id.equals(((EnrollCourse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrollCourse{" +
            "id=" + getId() +
            ", periodAcademic='" + getPeriodAcademic() + "'" +
            ", isEvaluated=" + getIsEvaluated() +
            "}";
    }
}
