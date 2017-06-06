package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.AircraftModel;
import com.malikov.ticketsystem.repository.IAircraftModelRepository;
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
public class AircraftModelRepositoryImpl implements IAircraftModelRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public AircraftModel save(AircraftModel aircraftModel) {
        if (aircraftModel.isNew()) {
            em.persist(aircraftModel);
            return aircraftModel;
        }
        return get(aircraftModel.getId()) != null ? em.merge(aircraftModel) : null;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM AircraftModel am WHERE am.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public AircraftModel get(long id) {
        return em.find(AircraftModel.class, id);
    }

    @Override
    public List<AircraftModel> getAll() {
        return em.createQuery("SELECT am FROM AircraftModel am ORDER BY am.id ASC", AircraftModel.class)
                .getResultList();
    }
}
