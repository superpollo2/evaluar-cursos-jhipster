package com.evaluar_cursos.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CourseQuestion.
 */
@Entity
@Table(name = "course_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourseQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course_question")
    private String courseQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseQuestion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseQuestion() {
        return this.courseQuestion;
    }

    public CourseQuestion courseQuestion(String courseQuestion) {
        this.setCourseQuestion(courseQuestion);
        return this;
    }

    public void setCourseQuestion(String courseQuestion) {
        this.courseQuestion = courseQuestion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseQuestion)) {
            return false;
        }
        return id != null && id.equals(((CourseQuestion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseQuestion{" +
            "id=" + getId() +
            ", courseQuestion='" + getCourseQuestion() + "'" +
            "}";
    }
}
