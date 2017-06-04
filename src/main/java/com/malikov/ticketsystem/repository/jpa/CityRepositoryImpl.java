package com.malikov.ticketsystem.repository.jpa;

import com.malikov.ticketsystem.model.City;
import com.malikov.ticketsystem.repository.ICityRepository;
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
public class CityRepositoryImpl implements ICityRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public City save(City city) {
        if (city.isNew()){
            em.persist((city));
            return city;
        } else {
            return em.merge(city);
        }
    }

    @Override
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
