package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Aircraft;
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
@Transactional
public class JpaAircraftRepositoryImpl implements IAircraftRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public Aircraft save(Aircraft aircraft) {
        if (aircraft.isNew()){
            em.persist((aircraft));
            return aircraft;
        } else {
            return em.merge(aircraft);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM Aircraft a WHERE a.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Aircraft get(long id, String... hintNames) {
        return em.find(Aircraft.class, id);
    }

    @Override
    public List<Aircraft> getAll() {
        return em.createQuery("SELECT a FROM Aircraft a ORDER BY a.id ASC", Aircraft.class)
                .getResultList();
    }

    @Override
    public List<Aircraft> getByNameMask(String nameMask) {
        return em.createQuery("SELECT a FROM Aircraft a WHERE lower(a.name) LIKE lower(:nameMask) ORDER BY a.id ASC",
                Aircraft.class)
                .setParameter("nameMask", '%' + nameMask + '%')
                .getResultList();
    }

    @Override
    public Aircraft getByName(String name) {
        List<Aircraft> airports =  em.createQuery("SELECT a FROM Aircraft a WHERE lower(a.name) = lower(:name) ORDER BY a.id ASC",
                Aircraft.class)
                .setParameter("name", name).getResultList();
        return DataAccessUtils.singleResult(airports);
    }
}
