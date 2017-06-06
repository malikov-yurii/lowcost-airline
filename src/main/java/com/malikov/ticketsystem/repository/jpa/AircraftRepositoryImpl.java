package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.model.AircraftModel;
import com.malikov.ticketsystem.repository.IAircraftRepository;
import org.springframework.dao.support.DataAccessUtils;
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
public class AircraftRepositoryImpl implements IAircraftRepository {

    @PersistenceContext
    protected EntityManager em;
    
    @Override
    @Transactional
    public Aircraft save(Aircraft aircraft) {
        aircraft.setModel(em.getReference(AircraftModel.class, aircraft.getModel().getId()));
        if (aircraft.isNew()){
            em.persist(aircraft);
            return aircraft;
        }
        return get(aircraft.getId()) != null ? em.merge(aircraft) : null;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM Aircraft a WHERE a.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Aircraft get(long id) {
        return em.find(Aircraft.class, id);
    }

    @Override
    public List<Aircraft> getAll() {
        return em.createQuery("SELECT a FROM Aircraft a ORDER BY a.id ASC", Aircraft.class)
                .getResultList();
    }

    @Override
    public Aircraft getByName(String name) {
        List<Aircraft> airports =  em.createQuery("SELECT a FROM Aircraft a " +
                        "WHERE lower(a.name) = lower(:name) ORDER BY a.id ASC", Aircraft.class)
                .setParameter("name", name).getResultList();
        return DataAccessUtils.singleResult(airports);
    }

    @Override
    public List<Aircraft> getByNameMask(String nameMask) {
        return em.createQuery("SELECT a FROM Aircraft a " +
                        "WHERE lower(a.name) LIKE lower(:nameMask) ORDER BY a.id ASC", Aircraft.class)
                .setParameter("nameMask", '%' + nameMask + '%')
                .getResultList();
    }
}
