package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Role;
import com.malikov.ticketsystem.repository.IRoleRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class RoleRepositoryImpl implements IRoleRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public Role save(Role role) {
        if (role.isNew()) {
            em.persist((role));
            return role;
        } else {
            return em.merge(role);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM Role r WHERE r.id = :id")
                .setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Role get(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Role> getAll() {
        return em.createQuery("SELECT r FROM Role r ORDER BY r.id ASC", Role.class)
                .getResultList();
    }

    @Override
    public Role getByName(String roleName) {
        List<Role> roles = em.createQuery("SELECT r FROM Role r WHERE r.name=:roleName", Role.class)
                .setParameter("roleName", roleName).getResultList();
        // TODO: 6/3/2017 Single resultList??
        return DataAccessUtils.singleResult(roles);
    }
}
