package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.repository.IFlightRepository;
import com.malikov.ticketsystem.util.MutableBoolean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.HashMap;
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
        // TODO: 5/29/2017 exclude in query full flights count(t) >= flight.aircraft.aircraftModel.passengersSeatsQuantity !! consider usage of native query
        String queryString = "SELECT f, COUNT(t)"
                + " FROM Flight f"
                + " JOIN FETCH f.tickets AS t"
                //+ " JOIN f.aircraft"
                //+ " JOIN f.aircraft.model"
                + " WHERE f.departureAirport=:departureAirport"
                + " AND f.arrivalAirport=:arrivalAirport"
                + " AND f.departureUtcDateTime>=:fromDepartureUtcDateTime"
                + " AND f.departureUtcDateTime<=:toDepartureUtcDateTime"
                + " GROUP BY t.flight"
                //+ " HAVING COUNT(t) < f.aircraft.model.passengerSeatsQuantity"
                + " ORDER BY f.id ASC";

        Query query = em.createQuery(queryString);
        query.setParameter("departureAirport", departureAirportCondition);
        query.setParameter("arrivalAirport", arrivalAirportCondition);
        query.setParameter("fromDepartureUtcDateTime", fromDepartureUtcDateTimeCondition);
        query.setParameter("toDepartureUtcDateTime", toDepartureUtcDateTimeCondition);
        // TODO: 6/5/2017 below done in memory! Should i first query id and than query only them using setFirst setMax?
        query.setFirstResult(first);
        query.setMaxResults(limit);

        // TODO: 6/5/2017 below is looking awful
        return (Map<Flight, Long>) query.getResultList().stream()
                .filter(resultElement ->
                        ((Flight) (((Object[]) resultElement)[0])).getAircraft().getModel().getPassengerSeatsQuantity()
                                > (Long) (((Object[]) resultElement)[1]))
                .collect(Collectors.toMap(resultElement -> (Flight) (((Object[]) resultElement)[0]),
                        resultElement -> (Long) (((Object[]) resultElement)[1])));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Flight> getFiltered(Airport departureAirportCondition, Airport arrivalAirportCondition,
                                    LocalDateTime fromDepartureUtcDateTimeCondition,
                                    LocalDateTime toDepartureUtcDateTimeCondition,
                                    Integer first, Integer limit) {
        // TODO: 6/5/2017 Maybe I should use criteria instead?
        StringBuilder builder = new StringBuilder("SELECT f FROM Flight f");
        Map<String, Object> params = buildWhereQueryClause(departureAirportCondition, arrivalAirportCondition,
                fromDepartureUtcDateTimeCondition, toDepartureUtcDateTimeCondition, builder);

        builder.append("  ORDER BY f.id ASC");
        Query query = em.createQuery(builder.toString());

        if (params != null) {
            for (Parameter parameter : query.getParameters()) {
                query.setParameter(parameter, params.get(parameter.getName()));
            }
        }
        query.setFirstResult(first);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    private Map<String, Object> buildWhereQueryClause(Airport departureAirportCondition, Airport arrivalAirportCondition,
            LocalDateTime fromDepartureUtcDateTimeCondition, LocalDateTime toDepartureUtcDateTimeCondition,
                     StringBuilder builder) {
        Map<String, Object> params = null;
        if (departureAirportCondition != null || arrivalAirportCondition != null ||
                toDepartureUtcDateTimeCondition != null || fromDepartureUtcDateTimeCondition != null) {
            params = new HashMap<>();
            MutableBoolean alreadyHasWhereClause = new MutableBoolean(false);

            if (departureAirportCondition != null) {
                builder.append(getClauseKeyword(alreadyHasWhereClause))
                        .append(" f.departureAirport=:departureAirport");
                params.put("departureAirport", departureAirportCondition);
            }

            if (arrivalAirportCondition != null) {
                builder.append(getClauseKeyword(alreadyHasWhereClause))
                        .append(" f.arrivalAirport=:arrivalAirport");
                params.put("arrivalAirport", arrivalAirportCondition);
            }

            if (fromDepartureUtcDateTimeCondition != null) {
                builder.append(getClauseKeyword(alreadyHasWhereClause))
                        .append(" f.departureUtcDateTime>=:fromDepartureUtcDateTime");
                params.put("fromDepartureUtcDateTime", fromDepartureUtcDateTimeCondition);
            }

            if (toDepartureUtcDateTimeCondition != null) {
                builder.append(getClauseKeyword(alreadyHasWhereClause))
                        .append(" f.departureUtcDateTime<=:toDepartureUtcDateTime");
                params.put("toDepartureUtcDateTime", toDepartureUtcDateTimeCondition);
            }
        }

        return params;
    }

    private String getClauseKeyword(MutableBoolean alreadyHasWhereClause) {
        if (alreadyHasWhereClause.booleanValue()) {
            return " AND";
        } else {
            alreadyHasWhereClause.setValue(true);
            return " WHERE";
        }
    }
}


//@Override
//public Long countAllFiltered(Airport departureAirport, Airport arrivalAirport,
//                             LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime) {
//    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//    Root<Flight> root = criteriaQuery.from(Flight.class);
//
//    List<Predicate> filterPredicates = buildPredicatesList(criteriaBuilder, root, departureAirport, arrivalAirport,
//            fromDepartureUtcDateTime, toDepartureUtcDateTime);
//    if (filterPredicates.size() != 0) {
//        criteriaQuery.where(criteriaBuilder.and(filterPredicates.toArray(new Predicate[filterPredicates.size()])));
//    }
//
//    //criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Flight.class)));
//    criteriaQuery.select(criteriaBuilder.countDistinct(root));
//
//    return em.createQuery(criteriaQuery).getSingleResult();
//}




/*
    @Override
    @SuppressWarnings("unchecked")
    public List<Flight> getFiltered(Airport departureAirport, Airport arrivalAirport,
                                       LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime,
                                       Integer first, Integer limit) {

        */
/* good working solution 1
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);

        List<Predicate> filterPredicates = buildPredicatesList(criteriaBuilder, root, departureAirport, arrivalAirport,
                fromDepartureUtcDateTime, toDepartureUtcDateTime);

        if (filterPredicates.size() != 0) {
            criteriaQuery.where(criteriaBuilder.and(filterPredicates.toArray(new Predicate[filterPredicates.size()])));
        }

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));

        Query query = em.createQuery(criteriaQuery);
        query.setFirstResult(first);
        query.setMaxResults(limit);
*//*

        return query.getResultList();
    }
*/

//@Override
//public Long countAllFiltered(Airport departureAirport, Airport arrivalAirport,
//                            LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime) {
//    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//    Root<Flight> root = criteriaQuery.from(Flight.class);
//
//    List<Predicate> filterPredicates = buildPredicatesList(criteriaBuilder, root, departureAirport, arrivalAirport,
//            fromDepartureUtcDateTime, toDepartureUtcDateTime);
//    if (filterPredicates.size() != 0) {
//        criteriaQuery.where(criteriaBuilder.and(filterPredicates.toArray(new Predicate[filterPredicates.size()])));
//    }
//
//    //criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Flight.class)));
//    criteriaQuery.select(criteriaBuilder.countDistinct(root));
//
//    return em.createQuery(criteriaQuery).getSingleResult();
//}
//
//@Override
//@SuppressWarnings("unchecked")
//public List<Flight> getFiltered(Airport departureAirport, Airport arrivalAirport,
//                                   LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime,
//                                   Integer first, Integer limit) {
//    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//    CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
//    Root<Flight> root = criteriaQuery.from(Flight.class);
//
//    List<Predicate> filterPredicates = buildPredicatesList(criteriaBuilder, root, departureAirport, arrivalAirport,
//            fromDepartureUtcDateTime, toDepartureUtcDateTime);
//
//    if (filterPredicates.size() != 0) {
//        criteriaQuery.where(criteriaBuilder.and(filterPredicates.toArray(new Predicate[filterPredicates.size()])));
//    }
//
//    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
//
//    Query query = em.createQuery(criteriaQuery);
//    query.setFirstResult(first);
//    query.setMaxResults(limit);
//
//    return query.getResultList();
//}

//private List<Predicate> buildPredicatesList (CriteriaBuilder criteriaBuilder, Root < Flight > root,
//        Airport departureAirport, Airport arrivalAirport,
//        LocalDateTime fromDepartureUtcDateTime,
//        LocalDateTime toDepartureUtcDateTime){
//    if (departureAirport != null || arrivalAirport != null
//            || fromDepartureUtcDateTime != null || toDepartureUtcDateTime != null) {
//        final List<Predicate> predicates = new ArrayList<>();
//
//        if (departureAirport != null) {
//            predicates.add(criteriaBuilder.equal(root.get("departureAirport"), departureAirport));
//        }
//
//        if (arrivalAirport != null) {
//            predicates.add(criteriaBuilder.equal(root.get("arrivalAirport"), arrivalAirport));
//        }
//
//        if (fromDepartureUtcDateTime != null) {
//            predicates.add(criteriaBuilder.greaterThan(root.get("departureUtcDateTime"), fromDepartureUtcDateTime));
//        }
//
//        if (toDepartureUtcDateTime != null) {
//            predicates.add(criteriaBuilder.lessThan(root.get("departureUtcDateTime"), toDepartureUtcDateTime));
//        }
//
//        return predicates;
//    }
//
//    return Collections.emptyList();
//}
//}

//    if (toDepartureUtcDateTime != null) {
//        builder.append(alreadyHasParams ? " AND" : " WHERE");
//        builder.append(" f.departureUtcDateTime>=:fromDepartureUtcDateTime");
//        params.put("fromDepartureUtcDateTime", fromDepartureUtcDateTime);
//    }

//StringBuilder builder = new StringBuilder("SELECT f FROM Flight f");
//
//if (departureAirport != null || arrivalAirport != null ||
//        toDepartureUtcDateTime != null || fromDepartureUtcDateTime != null) {
//
//    Map<String, Object> params = new HashMap<>();
//    boolean alreadyHasParams = false;
//
//    if (departureAirport != null) {
//        builder.append(alreadyHasParams ? " AND" : " WHERE");
//        builder.append(" f.departureAirport=:departureAirport");
//        params.put("departureAirport", departureAirport);
//    }
//
//    if (arrivalAirport != null) {
//        builder.append(alreadyHasParams ? " AND" : " WHERE");
//        builder.append(" f.arrivalAirport=:arrivalAirport");
//        params.put("arrivalAirport", arrivalAirport);
//    }
//
//    if (fromDepartureUtcDateTime != null) {
//        builder.append(alreadyHasParams ? " AND" : " WHERE");
//        builder.append(" f.departureUtcDateTime>=:fromDepartureUtcDateTime");
//        params.put("fromDepartureUtcDateTime", fromDepartureUtcDateTime);
//    }
//
//    if (toDepartureUtcDateTime != null) {
//        builder.append(alreadyHasParams ? " AND" : " WHERE");
//        builder.append(" f.departureUtcDateTime>=:fromDepartureUtcDateTime");
//        params.put("fromDepartureUtcDateTime", fromDepartureUtcDateTime);
//    }
//}

//@NamedQueries({
//        @NamedQuery(name = Flight.DELETE, query = "DELETE FROM Flight f WHERE f.id = :id"),
//        @NamedQuery(name = Flight.ALL_SORTED, query = "SELECT f FROM Flight f ORDER BY f.id ASC"),
//        @NamedQuery(name = Flight.ALL_FILTERED_SORTED_BY_ID, query =
//                "SELECT f FROM Flight f" +
//                        " WHERE ((:departureAirport is null or f.departureAirport = :departureAirport)" +
//                        " AND (:arrivalAirport is null or f.arrivalAirport=:arrivalAirport)" +
//                        " AND (:fromDepartureUtcDateTime is null or f.departureUtcDateTime >= :fromDepartureUtcDateTime)" +
//                        " AND (:toDepartureUtcDateTime is null or f.departureUtcDateTime <= :toDepartureUtcDateTime))" +
//                        " ORDER BY f.id ASC")
//})

//return em.createNamedQuery(Flight.ALL_FILTERED_SORTED_BY_ID, Flight.class)
//        .setParameter("departureAirport", departureAirport)
//        .setParameter("arrivalAirport", arrivalAirport)
//        .setParameter("fromDepartureUtcDateTime", fromDepartureUtcDateTime)
//        .setParameter("toDepartureUtcDateTime", toDepartureUtcDateTime)
//        .setFirstResult(first)
//        .setMaxResults(limit)
//        .getResultList();

        /*
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

        return criteria.list();*/


