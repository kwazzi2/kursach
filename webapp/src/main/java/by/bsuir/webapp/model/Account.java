package by.bsuir.webapp.model;


import by.bsuir.webapp.common.domain.SimpleEntity;

import javax.persistence.*;

@Entity
@Table
public class Account extends SimpleEntity {
    @Column(nullable = false, insertable = true, updatable = true)
    private String email;
    @Column(nullable = false, insertable = true, updatable = true)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, insertable = true, updatable = true)
    private String firstName;
    @Column(nullable = true, insertable = true, updatable = true)
    private String middleName;
    @Column(nullable = false, insertable = true, updatable = true)
    private String lastName;
    @Column(nullable = false, insertable = true, updatable = true)
    private boolean enabled;
    @Column(nullable = false, insertable = true, updatable = true)
    private boolean locked;
    @Column()
    private String hash;

    public enum Role {
        USER,
        TUTOR,
        ADMIN
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
