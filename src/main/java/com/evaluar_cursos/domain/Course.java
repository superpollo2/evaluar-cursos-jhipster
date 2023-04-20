package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Type(type = "uuid-char")
    @Column(name = "course_id", length = 36)
    private UUID courseId;

    @Column(name = "course_name")
    private String courseName;

    @OneToMany(mappedBy = "course")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "academicPeriod", "course", "profesor" }, allowSetters = true)
    private Set<TotalScoreCourse> totalScoreCourses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCourseId() {
        return this.courseId;
    }

    public Course courseId(UUID courseId) {
        this.setCourseId(courseId);
        return this;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public Course courseName(String courseName) {
        this.setCourseName(courseName);
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Set<TotalScoreCourse> getTotalScoreCourses() {
        return this.totalScoreCourses;
    }

    public void setTotalScoreCourses(Set<TotalScoreCourse> totalScoreCourses) {
        if (this.totalScoreCourses != null) {
            this.totalScoreCourses.forEach(i -> i.setCourse(null));
        }
        if (totalScoreCourses != null) {
            totalScoreCourses.forEach(i -> i.setCourse(this));
        }
        this.totalScoreCourses = totalScoreCourses;
    }

    public Course totalScoreCourses(Set<TotalScoreCourse> totalScoreCourses) {
        this.setTotalScoreCourses(totalScoreCourses);
        return this;
    }

    public Course addTotalScoreCourse(TotalScoreCourse totalScoreCourse) {
        this.totalScoreCourses.add(totalScoreCourse);
        totalScoreCourse.setCourse(this);
        return this;
    }

    public Course removeTotalScoreCourse(TotalScoreCourse totalScoreCourse) {
        this.totalScoreCourses.remove(totalScoreCourse);
        totalScoreCourse.setCourse(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseId='" + getCourseId() + "'" +
            ", courseName='" + getCourseName() + "'" +
            "}";
    }
}
