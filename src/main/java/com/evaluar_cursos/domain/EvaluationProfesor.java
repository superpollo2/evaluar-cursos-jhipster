package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EvaluationProfesor.
 */
@Entity
@Table(name = "evaluation_profesor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EvaluationProfesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "p_score_question_one")
    private Integer pScoreQuestionOne;

    @Column(name = "p_score_question_two")
    private Integer pScoreQuestionTwo;

    @Column(name = "p_score_question_three")
    private Integer pScoreQuestionThree;

    @Column(name = "p_score_question_four")
    private Integer pScoreQuestionFour;

    @Column(name = "p_score_question_five")
    private Integer pScoreQuestionFive;

    @Column(name = "p_score_question_six")
    private Integer pScoreQuestionSix;

    @JsonIgnoreProperties(value = { "student", "academicProgram", "profesor", "academicPeriod" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private EnrollCourse enrollCourse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EvaluationProfesor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getpScoreQuestionOne() {
        return this.pScoreQuestionOne;
    }

    public EvaluationProfesor pScoreQuestionOne(Integer pScoreQuestionOne) {
        this.setpScoreQuestionOne(pScoreQuestionOne);
        return this;
    }

    public void setpScoreQuestionOne(Integer pScoreQuestionOne) {
        this.pScoreQuestionOne = pScoreQuestionOne;
    }

    public Integer getpScoreQuestionTwo() {
        return this.pScoreQuestionTwo;
    }

    public EvaluationProfesor pScoreQuestionTwo(Integer pScoreQuestionTwo) {
        this.setpScoreQuestionTwo(pScoreQuestionTwo);
        return this;
    }

    public void setpScoreQuestionTwo(Integer pScoreQuestionTwo) {
        this.pScoreQuestionTwo = pScoreQuestionTwo;
    }

    public Integer getpScoreQuestionThree() {
        return this.pScoreQuestionThree;
    }

    public EvaluationProfesor pScoreQuestionThree(Integer pScoreQuestionThree) {
        this.setpScoreQuestionThree(pScoreQuestionThree);
        return this;
    }

    public void setpScoreQuestionThree(Integer pScoreQuestionThree) {
        this.pScoreQuestionThree = pScoreQuestionThree;
    }

    public Integer getpScoreQuestionFour() {
        return this.pScoreQuestionFour;
    }

    public EvaluationProfesor pScoreQuestionFour(Integer pScoreQuestionFour) {
        this.setpScoreQuestionFour(pScoreQuestionFour);
        return this;
    }

    public void setpScoreQuestionFour(Integer pScoreQuestionFour) {
        this.pScoreQuestionFour = pScoreQuestionFour;
    }

    public Integer getpScoreQuestionFive() {
        return this.pScoreQuestionFive;
    }

    public EvaluationProfesor pScoreQuestionFive(Integer pScoreQuestionFive) {
        this.setpScoreQuestionFive(pScoreQuestionFive);
        return this;
    }

    public void setpScoreQuestionFive(Integer pScoreQuestionFive) {
        this.pScoreQuestionFive = pScoreQuestionFive;
    }

    public Integer getpScoreQuestionSix() {
        return this.pScoreQuestionSix;
    }

    public EvaluationProfesor pScoreQuestionSix(Integer pScoreQuestionSix) {
        this.setpScoreQuestionSix(pScoreQuestionSix);
        return this;
    }

    public void setpScoreQuestionSix(Integer pScoreQuestionSix) {
        this.pScoreQuestionSix = pScoreQuestionSix;
    }

    public EnrollCourse getEnrollCourse() {
        return this.enrollCourse;
    }

    public void setEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourse = enrollCourse;
    }

    public EvaluationProfesor enrollCourse(EnrollCourse enrollCourse) {
        this.setEnrollCourse(enrollCourse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluationProfesor)) {
            return false;
        }
        return id != null && id.equals(((EvaluationProfesor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationProfesor{" +
            "id=" + getId() +
            ", pScoreQuestionOne=" + getpScoreQuestionOne() +
            ", pScoreQuestionTwo=" + getpScoreQuestionTwo() +
            ", pScoreQuestionThree=" + getpScoreQuestionThree() +
            ", pScoreQuestionFour=" + getpScoreQuestionFour() +
            ", pScoreQuestionFive=" + getpScoreQuestionFive() +
            ", pScoreQuestionSix=" + getpScoreQuestionSix() +
            "}";
    }
}
