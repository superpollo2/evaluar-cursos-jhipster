package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A UserO.
 */
@Entity
@Table(name = "user_o")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Type(type = "uuid-char")
    @Column(name = "user_name", length = 36)
    private UUID userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userOS" }, allowSetters = true)
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserO id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUserName() {
        return this.userName;
    }

    public UserO userName(UUID userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(UUID userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public UserO password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public UserO email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserO role(Role role) {
        this.setRole(role);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserO)) {
            return false;
        }
        return id != null && id.equals(((UserO) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
