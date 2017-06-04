package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.repository.IAirportRepository;
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
public class JpaAirportRepositoryImpl implements IAirportRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public Airport save(Airport Airport) {
        if (Airport.isNew()){
            em.persist((Airport));
            return Airport;
        } else {
            return em.merge(Airport);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM Airport a WHERE a.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Airport get(long id, String... hintNames) {
        return em.find(Airport.class, id);
    }

    @Override
    public List<Airport> getAll() {
        return em.createQuery("SELECT a FROM Airport a ORDER BY a.id ASC", Airport.class)
                .getResultList();
    }

    @Override
    public List<Airport> getByNameMask(String nameMask) {
        return em.createQuery("SELECT a FROM Airport a WHERE lower(a.name) LIKE lower(:nameMask) ORDER BY a.id ASC",
                Airport.class)
                .setParameter("nameMask", '%' + nameMask + '%')
                .getResultList();
    }

    // TODO: 5/22/2017 is it ok dto make name of airport unique but airport has id ????
    @Override
    public Airport getByName(String name) {
        List<Airport> airports =  em.createQuery("SELECT a FROM Airport a WHERE lower(a.name) = lower(:name) ORDER BY a.id ASC",
                Airport.class)
                .setParameter("name", name).getResultList();
        return DataAccessUtils.singleResult(airports);
    }
}
