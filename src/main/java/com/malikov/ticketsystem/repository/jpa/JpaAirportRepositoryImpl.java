package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.repository.IAirportRepository;
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
public class JpaAirportRepositoryImpl implements IAirportRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public Airport save(Airport Airport) {
        if (Airport.isNew()){
            em.persist((Airport));
            return Airport;
        } else {
            return em.merge(Airport);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(Airport.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Airport get(long id, String... hintNames) {
        return em.find(Airport.class, id);
    }

    @Override
    public List<Airport> getAll() {
        return em.createNamedQuery(Airport.ALL_SORTED, Airport.class).getResultList();
    }

}
