package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.AddressDao;
import test.pack.data.greenvine.entity.test.Address;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class AddressDaoImpl extends HibernateDaoSupport implements AddressDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#loadAddress(Integer)
     */
    @Override
    public Address loadAddress(Integer addressId) {
        return (Address) getHibernateTemplate().load(Address.class, addressId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#findAddress(Integer)
     */
    @Override
    public Address findAddress(Integer addressId) {
        return (Address) getHibernateTemplate().get(Address.class, addressId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#findAllAddresss()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Address> findAllAddresss() {
        return (List<Address>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Address as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#updateAddress(Address)
     */
    @Override 
    public void updateAddress(Address address) {
        getHibernateTemplate().merge(address);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#createAddress(Address)
     */
    @Override
    public void createAddress(Address address) {
        getHibernateTemplate().persist(address);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#removeAddress(Integer)
     */
    @Override
    public void removeAddress(Integer addressId) {
         Object address = getHibernateTemplate().load(Address.class, addressId);
         getHibernateTemplate().delete(address);

    }

}
