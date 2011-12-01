package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.CustomerDao;
import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#loadCustomer(PersonIdentity)
     */
    @Override
    public Customer loadCustomer(PersonIdentity person) {
        return (Customer) getHibernateTemplate().load(Customer.class, person);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#findCustomer(PersonIdentity)
     */
    @Override
    public Customer findCustomer(PersonIdentity person) {
        return (Customer) getHibernateTemplate().get(Customer.class, person);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#findAllCustomers()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Customer> findAllCustomers() {
        return (List<Customer>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Customer as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#updateCustomer(Customer)
     */
    @Override 
    public void updateCustomer(Customer customer) {
        getHibernateTemplate().merge(customer);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#createCustomer(Customer)
     */
    @Override
    public void createCustomer(Customer customer) {
        getHibernateTemplate().persist(customer);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#removeCustomer(PersonIdentity)
     */
    @Override
    public void removeCustomer(PersonIdentity person) {
         Object customer = getHibernateTemplate().load(Customer.class, person);
         getHibernateTemplate().delete(customer);

    }

}
