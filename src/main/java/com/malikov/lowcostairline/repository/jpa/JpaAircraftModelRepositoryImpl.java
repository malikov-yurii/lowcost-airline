package com.malikov.lowcostairline.repository.jpa;

import com.malikov.lowcostairline.model.AircraftModel;
import com.malikov.lowcostairline.repository.IAircraftModelRepository;
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
public class JpaAircraftModelRepositoryImpl implements IAircraftModelRepository {

    // TODO: 5/6/2017 should I create? JpaAbstractRepository and put there EnitityManager declaration
    @PersistenceContext
    protected EntityManager em;
    
    @Override
    public AircraftModel save(AircraftModel aircraftModel) {
        if (aircraftModel.isNew()){
            em.persist((aircraftModel));
            return aircraftModel;
        } else {
            return em.merge(aircraftModel);
        }
    }

    @Override
    public boolean delete(long id) {
        return em.createNamedQuery(AircraftModel.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public AircraftModel get(long id, String... hintNames) {
        return em.find(AircraftModel.class, id);
    }

    @Override
    public List<AircraftModel> getAll() {
        return em.createNamedQuery(AircraftModel.ALL_SORTED, AircraftModel.class).getResultList();
    }

}
