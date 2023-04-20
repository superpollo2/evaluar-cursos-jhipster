package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A AcademicPeriod.
 */
@Entity
@Table(name = "academic_period")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcademicPeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Type(type = "uuid-char")
    @Column(name = "period_id", length = 36)
    private UUID periodId;

    @Column(name = "init_period")
    private Instant initPeriod;

    @Column(name = "finish_period")
    private Instant finishPeriod;

    @OneToMany(mappedBy = "academicPeriod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "academicProgram", "profesor", "academicPeriod" }, allowSetters = true)
    private Set<EnrollCourse> enrollCourses = new HashSet<>();

    @OneToMany(mappedBy = "academicPeriod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "academicPeriod", "course", "profesor" }, allowSetters = true)
    private Set<TotalScoreCourse> totalScoreCourses = new HashSet<>();

    @OneToMany(mappedBy = "academicPeriod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "academicPeriod" }, allowSetters = true)
    private Set<TotalScoreProfesor> totalScoreProfesors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AcademicPeriod id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPeriodId() {
        return this.periodId;
    }

    public AcademicPeriod periodId(UUID periodId) {
        this.setPeriodId(periodId);
        return this;
    }

    public void setPeriodId(UUID periodId) {
        this.periodId = periodId;
    }

    public Instant getInitPeriod() {
        return this.initPeriod;
    }

    public AcademicPeriod initPeriod(Instant initPeriod) {
        this.setInitPeriod(initPeriod);
        return this;
    }

    public void setInitPeriod(Instant initPeriod) {
        this.initPeriod = initPeriod;
    }

    public Instant getFinishPeriod() {
        return this.finishPeriod;
    }

    public AcademicPeriod finishPeriod(Instant finishPeriod) {
        this.setFinishPeriod(finishPeriod);
        return this;
    }

    public void setFinishPeriod(Instant finishPeriod) {
        this.finishPeriod = finishPeriod;
    }

    public Set<EnrollCourse> getEnrollCourses() {
        return this.enrollCourses;
    }

    public void setEnrollCourses(Set<EnrollCourse> enrollCourses) {
        if (this.enrollCourses != null) {
            this.enrollCourses.forEach(i -> i.setAcademicPeriod(null));
        }
        if (enrollCourses != null) {
            enrollCourses.forEach(i -> i.setAcademicPeriod(this));
        }
        this.enrollCourses = enrollCourses;
    }

    public AcademicPeriod enrollCourses(Set<EnrollCourse> enrollCourses) {
        this.setEnrollCourses(enrollCourses);
        return this;
    }

    public AcademicPeriod addEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.add(enrollCourse);
        enrollCourse.setAcademicPeriod(this);
        return this;
    }

    public AcademicPeriod removeEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourses.remove(enrollCourse);
        enrollCourse.setAcademicPeriod(null);
        return this;
    }

    public Set<TotalScoreCourse> getTotalScoreCourses() {
        return this.totalScoreCourses;
    }

    public void setTotalScoreCourses(Set<TotalScoreCourse> totalScoreCourses) {
        if (this.totalScoreCourses != null) {
            this.totalScoreCourses.forEach(i -> i.setAcademicPeriod(null));
        }
        if (totalScoreCourses != null) {
            totalScoreCourses.forEach(i -> i.setAcademicPeriod(this));
        }
        this.totalScoreCourses = totalScoreCourses;
    }

    public AcademicPeriod totalScoreCourses(Set<TotalScoreCourse> totalScoreCourses) {
        this.setTotalScoreCourses(totalScoreCourses);
        return this;
    }

    public AcademicPeriod addTotalScoreCourse(TotalScoreCourse totalScoreCourse) {
        this.totalScoreCourses.add(totalScoreCourse);
        totalScoreCourse.setAcademicPeriod(this);
        return this;
    }

    public AcademicPeriod removeTotalScoreCourse(TotalScoreCourse totalScoreCourse) {
        this.totalScoreCourses.remove(totalScoreCourse);
        totalScoreCourse.setAcademicPeriod(null);
        return this;
    }

    public Set<TotalScoreProfesor> getTotalScoreProfesors() {
        return this.totalScoreProfesors;
    }

    public void setTotalScoreProfesors(Set<TotalScoreProfesor> totalScoreProfesors) {
        if (this.totalScoreProfesors != null) {
            this.totalScoreProfesors.forEach(i -> i.setAcademicPeriod(null));
        }
        if (totalScoreProfesors != null) {
            totalScoreProfesors.forEach(i -> i.setAcademicPeriod(this));
        }
        this.totalScoreProfesors = totalScoreProfesors;
    }

    public AcademicPeriod totalScoreProfesors(Set<TotalScoreProfesor> totalScoreProfesors) {
        this.setTotalScoreProfesors(totalScoreProfesors);
        return this;
    }

    public AcademicPeriod addTotalScoreProfesor(TotalScoreProfesor totalScoreProfesor) {
        this.totalScoreProfesors.add(totalScoreProfesor);
        totalScoreProfesor.setAcademicPeriod(this);
        return this;
    }

    public AcademicPeriod removeTotalScoreProfesor(TotalScoreProfesor totalScoreProfesor) {
        this.totalScoreProfesors.remove(totalScoreProfesor);
        totalScoreProfesor.setAcademicPeriod(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicPeriod)) {
            return false;
        }
        return id != null && id.equals(((AcademicPeriod) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicPeriod{" +
            "id=" + getId() +
            ", periodId='" + getPeriodId() + "'" +
            ", initPeriod='" + getInitPeriod() + "'" +
            ", finishPeriod='" + getFinishPeriod() + "'" +
            "}";
    }
}
