package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.ConsignmentDao;
import test.pack.data.greenvine.entity.test.Consignment;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class ConsignmentDaoImpl extends HibernateDaoSupport implements ConsignmentDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#loadConsignment(Integer)
     */
    @Override
    public Consignment loadConsignment(Integer consignmentId) {
        return (Consignment) getHibernateTemplate().load(Consignment.class, consignmentId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#findConsignment(Integer)
     */
    @Override
    public Consignment findConsignment(Integer consignmentId) {
        return (Consignment) getHibernateTemplate().get(Consignment.class, consignmentId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#findAllConsignments()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Consignment> findAllConsignments() {
        return (List<Consignment>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Consignment as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#updateConsignment(Consignment)
     */
    @Override 
    public void updateConsignment(Consignment consignment) {
        getHibernateTemplate().merge(consignment);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#createConsignment(Consignment)
     */
    @Override
    public void createConsignment(Consignment consignment) {
        getHibernateTemplate().persist(consignment);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.ConsignmentDao#removeConsignment(Integer)
     */
    @Override
    public void removeConsignment(Integer consignmentId) {
         Object consignment = getHibernateTemplate().load(Consignment.class, consignmentId);
         getHibernateTemplate().delete(consignment);

    }

}
