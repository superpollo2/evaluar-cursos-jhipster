package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TotalScoreCourse.
 */
@Entity
@Table(name = "total_score_course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TotalScoreCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "c_average_one")
    private Integer cAverageOne;

    @Column(name = "c_average_two")
    private Integer cAverageTwo;

    @Column(name = "c_average_three")
    private Integer cAverageThree;

    @Column(name = "c_average_four")
    private Integer cAverageFour;

    @Column(name = "c_average_five")
    private Integer cAverageFive;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enrollCourses", "totalScoreCourses", "totalScoreProfesors" }, allowSetters = true)
    private AcademicPeriod academicPeriod;

    @ManyToOne
    @JsonIgnoreProperties(value = { "totalScoreCourses" }, allowSetters = true)
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "enrollCourses", "totalScoreCourses" }, allowSetters = true)
    private Profesor profesor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TotalScoreCourse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getcAverageOne() {
        return this.cAverageOne;
    }

    public TotalScoreCourse cAverageOne(Integer cAverageOne) {
        this.setcAverageOne(cAverageOne);
        return this;
    }

    public void setcAverageOne(Integer cAverageOne) {
        this.cAverageOne = cAverageOne;
    }

    public Integer getcAverageTwo() {
        return this.cAverageTwo;
    }

    public TotalScoreCourse cAverageTwo(Integer cAverageTwo) {
        this.setcAverageTwo(cAverageTwo);
        return this;
    }

    public void setcAverageTwo(Integer cAverageTwo) {
        this.cAverageTwo = cAverageTwo;
    }

    public Integer getcAverageThree() {
        return this.cAverageThree;
    }

    public TotalScoreCourse cAverageThree(Integer cAverageThree) {
        this.setcAverageThree(cAverageThree);
        return this;
    }

    public void setcAverageThree(Integer cAverageThree) {
        this.cAverageThree = cAverageThree;
    }

    public Integer getcAverageFour() {
        return this.cAverageFour;
    }

    public TotalScoreCourse cAverageFour(Integer cAverageFour) {
        this.setcAverageFour(cAverageFour);
        return this;
    }

    public void setcAverageFour(Integer cAverageFour) {
        this.cAverageFour = cAverageFour;
    }

    public Integer getcAverageFive() {
        return this.cAverageFive;
    }

    public TotalScoreCourse cAverageFive(Integer cAverageFive) {
        this.setcAverageFive(cAverageFive);
        return this;
    }

    public void setcAverageFive(Integer cAverageFive) {
        this.cAverageFive = cAverageFive;
    }

    public AcademicPeriod getAcademicPeriod() {
        return this.academicPeriod;
    }

    public void setAcademicPeriod(AcademicPeriod academicPeriod) {
        this.academicPeriod = academicPeriod;
    }

    public TotalScoreCourse academicPeriod(AcademicPeriod academicPeriod) {
        this.setAcademicPeriod(academicPeriod);
        return this;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TotalScoreCourse course(Course course) {
        this.setCourse(course);
        return this;
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public TotalScoreCourse profesor(Profesor profesor) {
        this.setProfesor(profesor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TotalScoreCourse)) {
            return false;
        }
        return id != null && id.equals(((TotalScoreCourse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TotalScoreCourse{" +
            "id=" + getId() +
            ", cAverageOne=" + getcAverageOne() +
            ", cAverageTwo=" + getcAverageTwo() +
            ", cAverageThree=" + getcAverageThree() +
            ", cAverageFour=" + getcAverageFour() +
            ", cAverageFive=" + getcAverageFive() +
            "}";
    }
}
