package com.malikov.ticketsystem.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Yurii Malikov
 */
@Entity
@Table(name = "roles")
@AttributeOverride(name = "name", column = @Column(name = "role"))
public class Role extends NamedEntity implements GrantedAuthority{

    // TODO: 5/8/2017 write tests for role dao

    public Role() {}

    public Role(String name) {
        super(name);
    }

    public Role(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Role{" + getName() + "}";
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
