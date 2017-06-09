package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.City;
import com.malikov.ticketsystem.repository.ICityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CityRepositoryImpl implements ICityRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CityRepositoryImpl.class);

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public City save(City city) {
        if (city.isNew()) {
            em.persist(city);
            LOG.info("New {} created.", city);
            return city;
        }
        return get(city.getId()) != null ? em.merge(city) : null;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("DELETE FROM City c WHERE c.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public City get(long id) {
        return em.find(City.class, id);
    }

    @Override
    public List<City> getAll() {
        return em.createQuery("SELECT c FROM City c ORDER BY c.id ASC", City.class).getResultList();
    }
}
