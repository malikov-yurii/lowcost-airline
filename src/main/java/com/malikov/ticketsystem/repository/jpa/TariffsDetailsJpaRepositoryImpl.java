package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.repository.ITariffsDetailsRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class TariffsDetailsJpaRepositoryImpl implements ITariffsDetailsRepository{

    @PersistenceContext
    protected EntityManager em;

    @Override
    public TariffsDetails save(TariffsDetails tariffsDetails) {
        if (tariffsDetails.isNew()) {
            em.persist(tariffsDetails);
            return tariffsDetails;
        }
        return get(tariffsDetails.getId()) != null ? em.merge(tariffsDetails) : null;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM TariffsDetails td WHERE td.id = :id")
                .setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public TariffsDetails get(long id) {
        return em.find(TariffsDetails.class, id);
    }

    @Override
    public List<TariffsDetails> getAll() {
        return em.createQuery("SELECT td FROM TariffsDetails td ORDER BY td.id ASC", TariffsDetails.class)
                .getResultList();
    }

    @Override
    public TariffsDetails getActiveTariffsDetails() {
        return em.createQuery("SELECT td FROM TariffsDetails td WHERE td.active=true", TariffsDetails.class)
                .getSingleResult();
    }
}
