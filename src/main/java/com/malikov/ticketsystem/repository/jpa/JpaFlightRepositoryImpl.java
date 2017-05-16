package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.repository.IFlightRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Flight get(long id, String... hintNames) {
        Map<String, Object> hintsMap;
        // TODO: 5/8/2017 is it correct comparision??
        if (hintNames != null && hintNames.length != 0) {
            hintsMap = new HashMap<String, Object>();
            for (String hintName : hintNames) {
                hintsMap.put("javax.persistence.fetchgraph", em.getEntityGraph(hintName));
            }
            return em.find(Flight.class, id, hintsMap);
        }
        return em.find(Flight.class, id);
    }

    //@Override
    //public List<Flight> getAll() {
    //    return em.createNamedQuery(Flight.ALL_SORTED, Flight.class).getResultList();
    //}

    @Override
    public List<Flight> getAllBetween(Long departureAirportId, Long arrivalAirportId, LocalDateTime fromUtcDateTime, LocalDateTime toUtcDateTime) {
        return em.createNamedQuery(Flight.ALL_BETWEEN, Flight.class)
                .setParameter("departureAirportId", departureAirportId)
                .setParameter("arrivalAirportId", arrivalAirportId)
                .setParameter("fromUtcDateTime", fromUtcDateTime)
                .setParameter("toUtcDateTime", toUtcDateTime)
                .getResultList();
    }
}
