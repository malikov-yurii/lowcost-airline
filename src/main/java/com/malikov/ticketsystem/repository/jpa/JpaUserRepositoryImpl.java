package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.repository.IUserRepository;
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
@Transactional
public class JpaUserRepositoryImpl implements IUserRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()){
            em.persist((user));
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public User get(long id, String... hintNames) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class)
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
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter("email", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<String> getByEmailMask(String emailMask) {
        return em.createQuery("SELECT u.email FROM User u WHERE lower(u.email) LIKE lower(:emailMask) ORDER BY u.email ASC", String.class)
                // TODO: 5/20/2017 Move % to move % to query?
                .setParameter("emailMask", '%' + emailMask + '%')
                .getResultList();
    }

    @Override
    public List<String> getLastNamesBy(String lastNameMask) {
        return em.createQuery("SELECT u.lastName FROM User u WHERE lower(u.lastName) LIKE lower(:lastNameMask) ORDER BY u.lastName ASC", String.class)
                // TODO: 5/20/2017 Move % to move % to query?
                .setParameter("lastNameMask", '%' + lastNameMask + '%')
                .getResultList();
    }
}
