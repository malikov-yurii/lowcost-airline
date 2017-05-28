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
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter("email", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }
}
