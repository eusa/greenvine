package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.CustomerDao;
import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class CustomerDaoImpl extends JpaDaoSupport implements CustomerDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#loadCustomer(PersonIdentity)
     */
    @Override
    public Customer loadCustomer(PersonIdentity person) {
        return getJpaTemplate().getReference(Customer.class, person);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#findCustomer(PersonIdentity)
     */
    @Override
    public Customer findCustomer(PersonIdentity person) {
        return getJpaTemplate().find(Customer.class, person);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#findAllCustomers()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Customer> findAllCustomers() {
        return getJpaTemplate().find("from test.Customer as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#updateCustomer(Customer)
     */
    @Override 
    public void updateCustomer(Customer customer) {
        getJpaTemplate().merge(customer);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#createCustomer(Customer)
     */
    @Override
    public void createCustomer(Customer customer) {
        getJpaTemplate().persist(customer);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.CustomerDao#removeCustomer(PersonIdentity)
     */
    @Override
    public void removeCustomer(PersonIdentity person) {
         Customer customer = getJpaTemplate().getReference(Customer.class, person);
         getJpaTemplate().remove(customer);

    }

}
