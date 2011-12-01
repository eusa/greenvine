package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ConsignmentDao;
import test.pack.data.greenvine.entity.test.Consignment;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class ConsignmentDaoImpl extends JpaDaoSupport implements ConsignmentDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#loadConsignment(Integer)
     */
    @Override
    public Consignment loadConsignment(Integer consignmentId) {
        return getJpaTemplate().getReference(Consignment.class, consignmentId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#findConsignment(Integer)
     */
    @Override
    public Consignment findConsignment(Integer consignmentId) {
        return getJpaTemplate().find(Consignment.class, consignmentId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#findAllConsignments()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Consignment> findAllConsignments() {
        return getJpaTemplate().find("from test.Consignment as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#updateConsignment(Consignment)
     */
    @Override 
    public void updateConsignment(Consignment consignment) {
        getJpaTemplate().merge(consignment);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#createConsignment(Consignment)
     */
    @Override
    public void createConsignment(Consignment consignment) {
        getJpaTemplate().persist(consignment);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#removeConsignment(Integer)
     */
    @Override
    public void removeConsignment(Integer consignmentId) {
         Consignment consignment = getJpaTemplate().getReference(Consignment.class, consignmentId);
         getJpaTemplate().remove(consignment);

    }

}
