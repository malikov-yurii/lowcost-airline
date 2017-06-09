package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.City;
import com.malikov.ticketsystem.repository.IAirportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AirportRepositoryImpl implements IAirportRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AirportRepositoryImpl.class);

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public Airport save(Airport airport) {
        airport.setCity(em.getReference(City.class, airport.getCity().getId()));
        if (airport.isNew()) {
            em.persist(airport);
            LOG.info("New {} created.", airport);
            return airport;
        }
        return get(airport.getId()) != null ? em.merge(airport) : null;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM Airport a WHERE a.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Airport get(long id) {
        return em.find(Airport.class, id);
    }

    @Override
    public List<Airport> getAll() {
        return em.createQuery("SELECT a FROM Airport a ORDER BY a.id ASC", Airport.class)
                .getResultList();
    }

    @Override
    public Airport getByName(String name) {
        List<Airport> airports = em.createQuery("SELECT a FROM Airport a " +
                "WHERE lower(a.name) = lower(:name) ORDER BY a.id ASC", Airport.class)
                .setParameter("name", name).getResultList();
        return DataAccessUtils.singleResult(airports);
    }

    @Override
    public List<Airport> getByNameMask(String nameMask) {
        return em.createQuery("SELECT a FROM Airport a " +
                "WHERE lower(a.name) LIKE lower(:nameMask) ORDER BY a.id ASC", Airport.class)
                .setParameter("nameMask", '%' + nameMask + '%')
                .getResultList();
    }
}
