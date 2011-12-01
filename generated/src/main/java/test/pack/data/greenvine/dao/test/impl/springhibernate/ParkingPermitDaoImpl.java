package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ParkingPermitDao;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class ParkingPermitDaoImpl extends HibernateDaoSupport implements ParkingPermitDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#loadParkingPermit(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermit loadParkingPermit(ParkingPermitIdentity parkingPermitIdentity) {
        return (ParkingPermit) getHibernateTemplate().load(ParkingPermit.class, parkingPermitIdentity);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#findParkingPermit(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermit findParkingPermit(ParkingPermitIdentity parkingPermitIdentity) {
        return (ParkingPermit) getHibernateTemplate().get(ParkingPermit.class, parkingPermitIdentity);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#findAllParkingPermits()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<ParkingPermit> findAllParkingPermits() {
        return (List<ParkingPermit>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.ParkingPermit as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#updateParkingPermit(ParkingPermit)
     */
    @Override 
    public void updateParkingPermit(ParkingPermit parkingPermit) {
        getHibernateTemplate().merge(parkingPermit);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#createParkingPermit(ParkingPermit)
     */
    @Override
    public void createParkingPermit(ParkingPermit parkingPermit) {
        getHibernateTemplate().persist(parkingPermit);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitDao#removeParkingPermit(ParkingPermitIdentity)
     */
    @Override
    public void removeParkingPermit(ParkingPermitIdentity parkingPermitIdentity) {
         Object parkingPermit = getHibernateTemplate().load(ParkingPermit.class, parkingPermitIdentity);
         getHibernateTemplate().delete(parkingPermit);

    }

}
