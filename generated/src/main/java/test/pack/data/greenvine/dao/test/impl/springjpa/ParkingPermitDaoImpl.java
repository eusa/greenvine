package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ParkingPermitDao;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class ParkingPermitDaoImpl extends JpaDaoSupport implements ParkingPermitDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#loadParkingPermit(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermit loadParkingPermit(ParkingPermitIdentity parkingPermitIdentity) {
        return getJpaTemplate().getReference(ParkingPermit.class, parkingPermitIdentity);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#findParkingPermit(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermit findParkingPermit(ParkingPermitIdentity parkingPermitIdentity) {
        return getJpaTemplate().find(ParkingPermit.class, parkingPermitIdentity);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#findAllParkingPermits()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<ParkingPermit> findAllParkingPermits() {
        return getJpaTemplate().find("from test.ParkingPermit as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#updateParkingPermit(ParkingPermit)
     */
    @Override 
    public void updateParkingPermit(ParkingPermit parkingPermit) {
        getJpaTemplate().merge(parkingPermit);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#createParkingPermit(ParkingPermit)
     */
    @Override
    public void createParkingPermit(ParkingPermit parkingPermit) {
        getJpaTemplate().persist(parkingPermit);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#removeParkingPermit(ParkingPermitIdentity)
     */
    @Override
    public void removeParkingPermit(ParkingPermitIdentity parkingPermitIdentity) {
         ParkingPermit parkingPermit = getJpaTemplate().getReference(ParkingPermit.class, parkingPermitIdentity);
         getJpaTemplate().remove(parkingPermit);

    }

}
