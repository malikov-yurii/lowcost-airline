package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Role;

/**
 * @author Yurii Malikov
 */
public interface IRoleRepository extends IGenericRepository<Role> {

    /**
     * @return role found by role name or null if not found ane
     */
    Role getByName(String roleName);

}
