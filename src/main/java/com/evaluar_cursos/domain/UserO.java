package com.evaluar_cursos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A UserO.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "user_o")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserO implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userOS" }, allowSetters = true)
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getUserName() {
        return this.userName;
    }

    public UserO userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
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

    @Override
    public String getId() {
        return this.userName;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public UserO setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
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
        return userName != null && userName.equals(((UserO) o).userName);
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
            "userName=" + getUserName() +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
