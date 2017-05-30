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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional
public class JpaTicketRepositoryImpl implements ITicketRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;

    @Override
    public Integer countForFlight(Long flightId) {
        Query query = em.createQuery("SELECT count(t) FROM Ticket t WHERE t.flight.id=:flightId");
        query.setParameter("flightId", flightId);
        Long count = (Long)query.getSingleResult();
        return count != null ? count.intValue() : null;
    }

    // TODO: 5/30/2017 consider deleting this method. we dont need checking status? unnecessary??
    @Override
    public boolean deleteIfNotPaid(long ticketId) {
        Query query = em.createQuery("DELETE FROM Ticket t WHERE t.id=:ticketId AND t.status=:status");
        query.setParameter("ticketId", ticketId);
        query.setParameter("status", TicketStatus.BOOKED);
        return query.executeUpdate() != 0;
    }


    @Override
    public Ticket save(Ticket ticket, long userId) {
        if (!ticket.isNew() && get(ticket.getId(), userId) == null) {
            return null;
        }

        ticket.setUser(em.getReference(User.class, userId));
        if (ticket.isNew()) {
            em.persist((ticket));
            return ticket;
        } else {
            return em.merge(ticket);
        }
    }

    @Override
    public boolean delete(long id, long userId) {
        return em.createNamedQuery(Ticket.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Ticket get(long id, long userId, String... hintNames) {
        Map<String, Object> hintMap;
        Ticket ticket;
        // TODO: 5/8/2017 is it correct comparision??
        if (hintNames != null && hintNames.length != 0) {
            hintMap = new HashMap<String, Object>();
            for (String hintName : hintNames) {
                hintMap.put("javax.persistence.fetchgraph", em.getEntityGraph(hintName));
            }
            ticket = em.find(Ticket.class, id, hintMap);
        } else {
            ticket = em.find(Ticket.class, id);
        }
        return ticket != null && ticket.getUser().getId() == userId ? ticket : null;
    }

    @Override
    public List<Ticket> getAll(long userId) {
        return em.createNamedQuery(Ticket.ALL_SORTED, Ticket.class)
                .setParameter("userId", userId)
                .getResultList();
    }


}
