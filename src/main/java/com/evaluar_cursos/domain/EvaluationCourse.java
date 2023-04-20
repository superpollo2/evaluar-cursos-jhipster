package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EvaluationCourse.
 */
@Entity
@Table(name = "evaluation_course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EvaluationCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "c_score_question_one")
    private Integer cScoreQuestionOne;

    @Column(name = "c_score_question_two")
    private Integer cScoreQuestionTwo;

    @Column(name = "c_score_question_three")
    private Integer cScoreQuestionThree;

    @Column(name = "c_score_question_four")
    private Integer cScoreQuestionFour;

    @Column(name = "c_score_question_five")
    private Integer cScoreQuestionFive;

    @Column(name = "c_score_question_six")
    private Integer cScoreQuestionSix;

    @JsonIgnoreProperties(value = { "student", "academicProgram", "profesor", "academicPeriod" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private EnrollCourse enrollCourse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EvaluationCourse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getcScoreQuestionOne() {
        return this.cScoreQuestionOne;
    }

    public EvaluationCourse cScoreQuestionOne(Integer cScoreQuestionOne) {
        this.setcScoreQuestionOne(cScoreQuestionOne);
        return this;
    }

    public void setcScoreQuestionOne(Integer cScoreQuestionOne) {
        this.cScoreQuestionOne = cScoreQuestionOne;
    }

    public Integer getcScoreQuestionTwo() {
        return this.cScoreQuestionTwo;
    }

    public EvaluationCourse cScoreQuestionTwo(Integer cScoreQuestionTwo) {
        this.setcScoreQuestionTwo(cScoreQuestionTwo);
        return this;
    }

    public void setcScoreQuestionTwo(Integer cScoreQuestionTwo) {
        this.cScoreQuestionTwo = cScoreQuestionTwo;
    }

    public Integer getcScoreQuestionThree() {
        return this.cScoreQuestionThree;
    }

    public EvaluationCourse cScoreQuestionThree(Integer cScoreQuestionThree) {
        this.setcScoreQuestionThree(cScoreQuestionThree);
        return this;
    }

    public void setcScoreQuestionThree(Integer cScoreQuestionThree) {
        this.cScoreQuestionThree = cScoreQuestionThree;
    }

    public Integer getcScoreQuestionFour() {
        return this.cScoreQuestionFour;
    }

    public EvaluationCourse cScoreQuestionFour(Integer cScoreQuestionFour) {
        this.setcScoreQuestionFour(cScoreQuestionFour);
        return this;
    }

    public void setcScoreQuestionFour(Integer cScoreQuestionFour) {
        this.cScoreQuestionFour = cScoreQuestionFour;
    }

    public Integer getcScoreQuestionFive() {
        return this.cScoreQuestionFive;
    }

    public EvaluationCourse cScoreQuestionFive(Integer cScoreQuestionFive) {
        this.setcScoreQuestionFive(cScoreQuestionFive);
        return this;
    }

    public void setcScoreQuestionFive(Integer cScoreQuestionFive) {
        this.cScoreQuestionFive = cScoreQuestionFive;
    }

    public Integer getcScoreQuestionSix() {
        return this.cScoreQuestionSix;
    }

    public EvaluationCourse cScoreQuestionSix(Integer cScoreQuestionSix) {
        this.setcScoreQuestionSix(cScoreQuestionSix);
        return this;
    }

    public void setcScoreQuestionSix(Integer cScoreQuestionSix) {
        this.cScoreQuestionSix = cScoreQuestionSix;
    }

    public EnrollCourse getEnrollCourse() {
        return this.enrollCourse;
    }

    public void setEnrollCourse(EnrollCourse enrollCourse) {
        this.enrollCourse = enrollCourse;
    }

    public EvaluationCourse enrollCourse(EnrollCourse enrollCourse) {
        this.setEnrollCourse(enrollCourse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluationCourse)) {
            return false;
        }
        return id != null && id.equals(((EvaluationCourse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationCourse{" +
            "id=" + getId() +
            ", cScoreQuestionOne=" + getcScoreQuestionOne() +
            ", cScoreQuestionTwo=" + getcScoreQuestionTwo() +
            ", cScoreQuestionThree=" + getcScoreQuestionThree() +
            ", cScoreQuestionFour=" + getcScoreQuestionFour() +
            ", cScoreQuestionFive=" + getcScoreQuestionFive() +
            ", cScoreQuestionSix=" + getcScoreQuestionSix() +
            "}";
    }
}
