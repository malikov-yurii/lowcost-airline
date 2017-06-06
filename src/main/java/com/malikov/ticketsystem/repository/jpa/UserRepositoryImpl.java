package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Role;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.repository.IUserRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements IUserRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        // TODO: 6/6/2017 Is it ok?
        Set<Role> roleReferences = new HashSet();
        for (Role role : user.getRoles()) {
            roleReferences.add(em.getReference(Role.class, role.getId()));
        }
        user.setRoles(roleReferences);
        if (user.isNew()){
            em.persist((user));
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM User u WHERE u.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public User get(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.id ASC", User.class)
                .getResultList();
    }

    @Override
    public List<User> getByLastName(String lastName) {
        return em.createQuery("SELECT u FROM User u WHERE u.lastName=:lastName ORDER BY u.id", User.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class)
                .setParameter("email", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<String> getByEmailMask(String emailMask) {
        return em.createQuery("SELECT u.email FROM User u WHERE lower(u.email) LIKE lower(:emailMask) ORDER BY u.email ASC", String.class)
                .setParameter("emailMask", '%' + emailMask + '%')
                .getResultList();
    }

    @Override
    public List<String> getLastNamesBy(String lastNameMask) {
        return em.createQuery("SELECT u.lastName FROM User u WHERE lower(u.lastName) LIKE lower(:lastNameMask) ORDER BY u.lastName ASC", String.class)
                .setParameter("lastNameMask", '%' + lastNameMask + '%')
                .getResultList();
    }
}
