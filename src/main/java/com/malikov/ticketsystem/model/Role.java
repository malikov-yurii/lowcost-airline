package com.malikov.ticketsystem.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Yurii Malikov
 */
@Entity
// TODO: 6/3/2017 Unique constraint??
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"role"},
        name = "unique_role_constraint")})
@AttributeOverride(name = "name", column = @Column(name = "role"))
public class Role extends NamedEntity implements GrantedAuthority {

    public Role() {
    }


    public Role(Long id, String name) {
        super(id, name);
    }


    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return "Role{" + getName() + "}";
    }
}
