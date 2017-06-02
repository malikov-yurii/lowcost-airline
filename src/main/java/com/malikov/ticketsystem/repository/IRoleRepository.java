package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Role;

/**
 * @author Yurii Malikov
 */
public interface IRoleRepository extends IGenericRepository<Role> {

    Role getByName(String roleName);

}
