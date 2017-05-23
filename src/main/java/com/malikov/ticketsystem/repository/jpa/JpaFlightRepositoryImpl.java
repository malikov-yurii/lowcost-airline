package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.repository.IFlightRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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
        if (flight.isNew()) {
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

    @Override
    public List<Flight> getAll() {
        return em.createNamedQuery(Flight.ALL_SORTED, Flight.class).getResultList();
    }

    @Override
    public List<Flight> getAllBetween(Airport departureAirport, Airport arrivalAirport, LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime) {

        // TODO: 5/24/2017 I should replace it with CriteriaBuilder builder = em.getCriteriaBuilder();

        Session session = (Session) em.getDelegate();

        Criteria criteria = session.createCriteria(Flight.class);

        if (toDepartureUtcDateTime != null) {
            criteria.add(Restrictions.lt("departureUtcDateTime", toDepartureUtcDateTime));
        }

        if (fromDepartureUtcDateTime != null) {
            criteria.add(Restrictions.gt("departureUtcDateTime", fromDepartureUtcDateTime));
        }

        if (departureAirport != null) {
            criteria.add(Restrictions.eq("departureAirport", departureAirport));
        }

        if (arrivalAirport != null) {
            criteria.add(Restrictions.eq("arrivalAirport", arrivalAirport));
        }

        return criteria.list();
/*

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Metamodel metamodel = em.getMetamodel();
        EntityType<Flight> Flight_ = metamodel.entity(Flight.class);
        ManagedType<Flight> FFlight_ = metamodel.managedType(Flight.class);
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> flight = criteriaQuery.from(Flight.class);
        //criteriaQuery = criteriaQuery.where(criteriaBuilder.equal(flight.get(FFlight_.departureAirport), arrivalAirport));
        //criteriaQuery = criteriaQuery.where(criteriaBuilder.equal(flight.get(Flight_.getAttribute("departureAirport"), departureAirport));


        criteriaQuery.where(
                criteriaBuilder.gt(flight.get(Flight_.getSingularAttribute("departureUtcDateTime")), criteriaBuilder.literal(fromDepartureUtcDateTime)),
                criteriaBuilder.lt(flight.<LocalDateTime>get(Flight_.getName().getAttribute()getSingularAttribute("departureUtcDateTime")), criteriaBuilder.literal(toDepartureUtcDateTime))
                criteriaBuilder.lt(flight.<LocalDateTime>get(Flight_.departureUtcDateTime), criteriaBuilder.literal(toDepartureUtcDateTime))
        );

        return em.createQuery(criteriaQuery).getResultList();*/

        //return em.createNamedQuery(Flight.ALL_BETWEEN, Flight.class)
        //        .setParameter("departureAirport", departureAirport)
        //        .setParameter("arrivalAirport", arrivalAirport)
        //        .setParameter("fromUtcDateTime", fromDepartureUtcDateTime)
        //        .setParameter("toUtcDateTime", toDepartureUtcDateTime)
        //        .getResultList();
    }
}
