package com.malikov.lowcostairline.repository.jpa;

import com.malikov.lowcostairline.model.Ticket;
import com.malikov.lowcostairline.repository.ITicketRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Ticket get(long id, String... hintNames) {
        Map<String, Object> hintMap;
        // TODO: 5/8/2017 is it correct comparision??
        if (hintNames != null && hintNames.length != 0) {
            hintMap = new HashMap<String, Object>();
            for (String hintName : hintNames) {
                hintMap.put("javax.persistence.fetchgraph", em.getEntityGraph(hintName));
            }
            return em.find(Ticket.class, id, hintMap);
        }
        return em.find(Ticket.class, id);
    }

    @Override
    public List<Ticket> getAll() {
        return em.createNamedQuery(Ticket.ALL_SORTED, Ticket.class).getResultList();
    }

}
