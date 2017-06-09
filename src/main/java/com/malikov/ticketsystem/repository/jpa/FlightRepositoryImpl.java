package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.repository.IFlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class FlightRepositoryImpl implements IFlightRepository {

    private static final Logger LOG = LoggerFactory.getLogger(FlightRepositoryImpl.class);

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public Flight save(Flight flight) {
        flight.setAircraft(em.getReference(Aircraft.class, flight.getAircraft().getId()));
        flight.setDepartureAirport(em.getReference(Airport.class, flight.getDepartureAirport().getId()));
        flight.setArrivalAirport(em.getReference(Airport.class, flight.getArrivalAirport().getId()));
        if (flight.isNew()) {
            em.persist(flight);
            LOG.info("New {} created.", flight);
            return flight;
        }
        return get(flight.getId()) != null ? em.merge(flight) : null;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM Flight f WHERE f.id = :id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Flight get(long id) {
        return em.find(Flight.class, id);
    }

    @Override
    public List<Flight> getAll() {
        return em.createQuery("SELECT f FROM Flight f ORDER BY f.id ASC", Flight.class)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Flight, Long> getFilteredFlightTicketCountMap(Airport departureAirportCondition,
                                                             Airport arrivalAirportCondition,
                                                             LocalDateTime fromDepartureUtcDateTimeCondition,
                                                             LocalDateTime toDepartureUtcDateTimeCondition,
                                                             Integer first, Integer limit) {
        String queryString = "SELECT f, COUNT(t)"
                + " FROM Flight f"
                + " JOIN FETCH f.tickets AS t"
                + " JOIN f.aircraft"
                + " JOIN f.aircraft.model AS aircraftmo3_"
                + " WHERE f.departureAirport=:departureAirport"
                + " AND f.arrivalAirport=:arrivalAirport"
                + " AND f.departureUtcDateTime>=:fromDepartureUtcDateTime"
                + " AND f.departureUtcDateTime<=:toDepartureUtcDateTime"
                + " GROUP BY t.flight"
                + " ORDER BY f.id ASC";

        Query query = em.createQuery(queryString);
        query.setParameter("departureAirport", departureAirportCondition);
        query.setParameter("arrivalAirport", arrivalAirportCondition);
        query.setParameter("fromDepartureUtcDateTime", fromDepartureUtcDateTimeCondition);
        query.setParameter("toDepartureUtcDateTime", toDepartureUtcDateTimeCondition);
        query.setFirstResult(first);
        query.setMaxResults(limit);

        return (Map<Flight, Long>) query.getResultList().stream()
                .collect(Collectors.toMap(resultElement -> (Flight) (((Object[]) resultElement)[0]),
                                            resultElement -> (Long) (((Object[]) resultElement)[1])));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Flight> getFiltered(Airport departureAirport, Airport arrivalAirport,
                                    LocalDateTime fromDepartureUtcDateTime,
                                    LocalDateTime toDepartureUtcDateTime,
                                    Integer first, Integer limit) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);

        List<Predicate> filterPredicates = buildPredicatesList(criteriaBuilder, root,
                departureAirport, arrivalAirport, fromDepartureUtcDateTime, toDepartureUtcDateTime);

        if (filterPredicates.size() != 0) {
            criteriaQuery.where(criteriaBuilder.and(filterPredicates.toArray(new Predicate[filterPredicates.size()])));
        }

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));

        Query query = em.createQuery(criteriaQuery);
        query.setFirstResult(first);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    private List<Predicate> buildPredicatesList(CriteriaBuilder criteriaBuilder, Root<Flight> root,
                                                Airport departureAirport, Airport arrivalAirport,
                                                LocalDateTime fromDepartureUtcDateTime,
                                                LocalDateTime toDepartureUtcDateTime) {
        if (departureAirport != null || arrivalAirport != null
                || fromDepartureUtcDateTime != null || toDepartureUtcDateTime != null) {
            List<Predicate> predicates = new ArrayList<>();

            if (departureAirport != null) {
                predicates.add(criteriaBuilder.equal(root.get("departureAirport"), departureAirport));
            }

            if (arrivalAirport != null) {
                predicates.add(criteriaBuilder.equal(root.get("arrivalAirport"), arrivalAirport));
            }

            if (fromDepartureUtcDateTime != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("departureUtcDateTime"), fromDepartureUtcDateTime));
            }

            if (toDepartureUtcDateTime != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("departureUtcDateTime"), toDepartureUtcDateTime));
            }

            return predicates;
        }

        return Collections.emptyList();
    }
}