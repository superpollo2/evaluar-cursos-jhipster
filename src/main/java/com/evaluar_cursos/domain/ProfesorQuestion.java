package com.evaluar_cursos.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProfesorQuestion.
 */
@Entity
@Table(name = "profesor_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfesorQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "profesor_question")
    private String profesorQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProfesorQuestion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesorQuestion() {
        return this.profesorQuestion;
    }

    public ProfesorQuestion profesorQuestion(String profesorQuestion) {
        this.setProfesorQuestion(profesorQuestion);
        return this;
    }

    public void setProfesorQuestion(String profesorQuestion) {
        this.profesorQuestion = profesorQuestion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfesorQuestion)) {
            return false;
        }
        return id != null && id.equals(((ProfesorQuestion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfesorQuestion{" +
            "id=" + getId() +
            ", profesorQuestion='" + getProfesorQuestion() + "'" +
            "}";
    }
}
