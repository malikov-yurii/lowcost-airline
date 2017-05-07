package com.malikov.lowcostairline.repository.jpa;

import com.malikov.lowcostairline.model.Ticket;
import com.malikov.lowcostairline.repository.ITicketRepository;
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
public class JpaTicketRepositoryImpl implements ITicketRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public Ticket save(Ticket ticket) {
        if (ticket.isNew()){
            em.persist((ticket));
            return ticket;
        } else {
            return em.merge(ticket);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(Ticket.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Ticket get(long id) {
        return em.find(Ticket.class, id);
    }

    @Override
    public List<Ticket> getAll() {
        return em.createNamedQuery(Ticket.ALL_SORTED, Ticket.class).getResultList();
    }

}
