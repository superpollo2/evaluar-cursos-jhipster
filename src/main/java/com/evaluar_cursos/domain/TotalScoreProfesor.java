package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TotalScoreProfesor.
 */
@Entity
@Table(name = "total_score_profesor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TotalScoreProfesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "p_average_one")
    private Integer pAverageOne;

    @Column(name = "p_average_two")
    private Integer pAverageTwo;

    @Column(name = "p_average_three")
    private Integer pAverageThree;

    @Column(name = "p_average_four")
    private Integer pAverageFour;

    @Column(name = "p_average_five")
    private Integer pAverageFive;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enrollCourses", "totalScoreCourses", "totalScoreProfesors" }, allowSetters = true)
    private AcademicPeriod academicPeriod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TotalScoreProfesor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getpAverageOne() {
        return this.pAverageOne;
    }

    public TotalScoreProfesor pAverageOne(Integer pAverageOne) {
        this.setpAverageOne(pAverageOne);
        return this;
    }

    public void setpAverageOne(Integer pAverageOne) {
        this.pAverageOne = pAverageOne;
    }

    public Integer getpAverageTwo() {
        return this.pAverageTwo;
    }

    public TotalScoreProfesor pAverageTwo(Integer pAverageTwo) {
        this.setpAverageTwo(pAverageTwo);
        return this;
    }

    public void setpAverageTwo(Integer pAverageTwo) {
        this.pAverageTwo = pAverageTwo;
    }

    public Integer getpAverageThree() {
        return this.pAverageThree;
    }

    public TotalScoreProfesor pAverageThree(Integer pAverageThree) {
        this.setpAverageThree(pAverageThree);
        return this;
    }

    public void setpAverageThree(Integer pAverageThree) {
        this.pAverageThree = pAverageThree;
    }

    public Integer getpAverageFour() {
        return this.pAverageFour;
    }

    public TotalScoreProfesor pAverageFour(Integer pAverageFour) {
        this.setpAverageFour(pAverageFour);
        return this;
    }

    public void setpAverageFour(Integer pAverageFour) {
        this.pAverageFour = pAverageFour;
    }

    public Integer getpAverageFive() {
        return this.pAverageFive;
    }

    public TotalScoreProfesor pAverageFive(Integer pAverageFive) {
        this.setpAverageFive(pAverageFive);
        return this;
    }

    public void setpAverageFive(Integer pAverageFive) {
        this.pAverageFive = pAverageFive;
    }

    public AcademicPeriod getAcademicPeriod() {
        return this.academicPeriod;
    }

    public void setAcademicPeriod(AcademicPeriod academicPeriod) {
        this.academicPeriod = academicPeriod;
    }

    public TotalScoreProfesor academicPeriod(AcademicPeriod academicPeriod) {
        this.setAcademicPeriod(academicPeriod);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TotalScoreProfesor)) {
            return false;
        }
        return id != null && id.equals(((TotalScoreProfesor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TotalScoreProfesor{" +
            "id=" + getId() +
            ", pAverageOne=" + getpAverageOne() +
            ", pAverageTwo=" + getpAverageTwo() +
            ", pAverageThree=" + getpAverageThree() +
            ", pAverageFour=" + getpAverageFour() +
            ", pAverageFive=" + getpAverageFive() +
            "}";
    }
}
