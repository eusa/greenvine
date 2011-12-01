package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao;
import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class ParkingPermitPaymentDaoImpl extends JpaDaoSupport implements ParkingPermitPaymentDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#loadParkingPermitPayment(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermitPayment loadParkingPermitPayment(ParkingPermitIdentity parkingPermit) {
        return getJpaTemplate().getReference(ParkingPermitPayment.class, parkingPermit);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#findParkingPermitPayment(ParkingPermitIdentity)
     */
    @Override
    public ParkingPermitPayment findParkingPermitPayment(ParkingPermitIdentity parkingPermit) {
        return getJpaTemplate().find(ParkingPermitPayment.class, parkingPermit);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#findAllParkingPermitPayments()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<ParkingPermitPayment> findAllParkingPermitPayments() {
        return getJpaTemplate().find("from test.ParkingPermitPayment as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#updateParkingPermitPayment(ParkingPermitPayment)
     */
    @Override 
    public void updateParkingPermitPayment(ParkingPermitPayment parkingPermitPayment) {
        getJpaTemplate().merge(parkingPermitPayment);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#createParkingPermitPayment(ParkingPermitPayment)
     */
    @Override
    public void createParkingPermitPayment(ParkingPermitPayment parkingPermitPayment) {
        getJpaTemplate().persist(parkingPermitPayment);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ParkingPermitPaymentDao#removeParkingPermitPayment(ParkingPermitIdentity)
     */
    @Override
    public void removeParkingPermitPayment(ParkingPermitIdentity parkingPermit) {
         ParkingPermitPayment parkingPermitPayment = getJpaTemplate().getReference(ParkingPermitPayment.class, parkingPermit);
         getJpaTemplate().remove(parkingPermitPayment);

    }

}
