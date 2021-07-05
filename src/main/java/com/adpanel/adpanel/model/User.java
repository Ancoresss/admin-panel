package com.adpanel.adpanel.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role roles;
    @Transient
    private String isSubAdmin;

    public User() {
    }

    public User(String name, String password, Role roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRole(Role role) {
        this.roles = role;
    }

    public String isSubAdmin() {
        return isSubAdmin;
    }

    public void setSubAdmin(String subAdmin) {
        isSubAdmin = subAdmin;
    }
}
