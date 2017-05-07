package com.malikov.lowcostairline.repository.jpa;

import com.malikov.lowcostairline.model.Flight;
import com.malikov.lowcostairline.repository.IFlightRepository;
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
public class JpaFlightRepositoryImpl implements IFlightRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public Flight save(Flight flight) {
        if (flight.isNew()){
            em.persist((flight));
            return flight;
        } else {
            return em.merge(flight);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(Flight.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Flight get(long id) {
        return em.find(Flight.class, id);
    }

    @Override
    public List<Flight> getAll() {
        return em.createNamedQuery(Flight.ALL_SORTED, Flight.class).getResultList();
    }

}
