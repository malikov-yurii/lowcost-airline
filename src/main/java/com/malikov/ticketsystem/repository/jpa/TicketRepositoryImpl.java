package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.TicketStatus;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.repository.ITicketRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class TicketRepositoryImpl implements ITicketRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public List<Ticket> getActiveByUserId(long userId, Integer start, Integer limit) {
        return em.createQuery("SELECT t FROM Ticket t JOIN t.user AS u " +
                                        "WHERE u.id=:userId AND t.departureUtcDateTime>=:now " +
                                        "ORDER BY t.departureUtcDateTime " +
                                        "DESC", Ticket.class)
                .setParameter("userId", userId)
                .setParameter("now", LocalDateTime.now())
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Ticket> getArchivedByUserId(Long userId, Integer start, Integer limit) {
        return em.createQuery("SELECT t FROM Ticket t " +
                                        "JOIN t.user AS u " +
                                        "WHERE u.id=:userId AND t.departureUtcDateTime<:now " +
                                        "ORDER BY t.departureUtcDateTime DESC", Ticket.class)
                .setParameter("userId", userId)
                .setParameter("now", LocalDateTime.now())
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Integer> getOccupiedSeatsNumbers(Long flightId) {
        return em.createQuery("SELECT t.seatNumber FROM Ticket t " +
                                        "WHERE t.flight.id=:flightId", Integer.class)
                .setParameter("flightId", flightId)
                .getResultList();
    }

    @Override
    public Integer countTickets(Long flightId) {
        Query query = em.createQuery("SELECT count(t) FROM Ticket t WHERE t.flight.id=:flightId");
        query.setParameter("flightId", flightId);
        Long count = (Long)query.getSingleResult();
        return count != null ? count.intValue() : null;
    }

    @Override
    @Transactional
    public boolean deleteIfNotPaid(long ticketId) {
        Query query = em.createQuery("DELETE FROM Ticket t WHERE t.id=:ticketId AND t.status=:status");
        query.setParameter("ticketId", ticketId);
        query.setParameter("status", TicketStatus.BOOKED);
        return query.executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Ticket save(Ticket ticket) {
        ticket.setUser(em.getReference(User.class, ticket.getUser().getId()));
        if (ticket.isNew()){
            em.persist(ticket);
            return ticket;
        }
        return get(ticket.getId()) != null ? em.merge(ticket) : null;
    }

    @Override
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM Ticket t WHERE t.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Ticket get(long id) {
        return em.find(Ticket.class, id);
    }

    @Override
    public List<Ticket> getAll() {
        return em.createQuery("SELECT t FROM Ticket t ORDER BY t.id ASC", Ticket.class)
                .getResultList();
    }

    @Override
    public List<Ticket> getByEmail(String userEmail, Integer start, Integer limit) {
        return em.createQuery("SELECT t FROM Ticket t JOIN t.user AS u " +
                                        "WHERE u.email=:email " +
                                        "ORDER BY t.departureUtcDateTime DESC", Ticket.class)
                .setParameter("email", userEmail)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }
}
