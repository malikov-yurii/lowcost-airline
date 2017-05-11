package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.repository.IAircraftRepository;
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
public class JpaAircraftRepositoryImpl implements IAircraftRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public Aircraft save(Aircraft aircraft) {
        if (aircraft.isNew()){
            em.persist((aircraft));
            return aircraft;
        } else {
            return em.merge(aircraft);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(Aircraft.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Aircraft get(long id, String... hintNames) {
        return em.find(Aircraft.class, id);
    }

    @Override
    public List<Aircraft> getAll() {
        return em.createNamedQuery(Aircraft.ALL_SORTED, Aircraft.class).getResultList();
    }

}
