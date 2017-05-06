package com.malikov.lowcostairline.repository.jpa;

import com.malikov.lowcostairline.model.User;
import com.malikov.lowcostairline.repository.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * @author Yurii Malikov
 */
@Repository
@Transactional
public class JpaUserRepositoryImpl implements IUserRepository {

    @PersistenceContext
    private EntityManager em;

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
    public User get(long id) {
        return em.find(User.class, id);
    }

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }
}
