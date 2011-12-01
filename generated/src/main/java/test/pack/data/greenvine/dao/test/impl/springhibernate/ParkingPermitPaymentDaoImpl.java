package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao;
import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class ParkingPermitPaymentDaoImpl extends HibernateDaoSupport implements ParkingPermitPaymentDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#loadParkingPermitPayment(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermitPayment loadParkingPermitPayment(ParkingPermitIdentity parkingPermit) {
        return (ParkingPermitPayment) getHibernateTemplate().load(ParkingPermitPayment.class, parkingPermit);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#findParkingPermitPayment(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermitPayment findParkingPermitPayment(ParkingPermitIdentity parkingPermit) {
        return (ParkingPermitPayment) getHibernateTemplate().get(ParkingPermitPayment.class, parkingPermit);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#findAllParkingPermitPayments()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<ParkingPermitPayment> findAllParkingPermitPayments() {
        return (List<ParkingPermitPayment>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.ParkingPermitPayment as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#updateParkingPermitPayment(ParkingPermitPayment)
     */
    @Override 
    public void updateParkingPermitPayment(ParkingPermitPayment parkingPermitPayment) {
        getHibernateTemplate().merge(parkingPermitPayment);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#createParkingPermitPayment(ParkingPermitPayment)
     */
    @Override
    public void createParkingPermitPayment(ParkingPermitPayment parkingPermitPayment) {
        getHibernateTemplate().persist(parkingPermitPayment);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#removeParkingPermitPayment(ParkingPermitIdentity)
     */
    @Override
    public void removeParkingPermitPayment(ParkingPermitIdentity parkingPermit) {
         Object parkingPermitPayment = getHibernateTemplate().load(ParkingPermitPayment.class, parkingPermit);
         getHibernateTemplate().delete(parkingPermitPayment);

    }

}
