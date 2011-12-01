package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.AddressDao;
import test.pack.data.greenvine.entity.test.Address;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class AddressDaoImpl extends JpaDaoSupport implements AddressDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#loadAddress(Integer)
     */
    @Override
    public Address loadAddress(Integer addressId) {
        return getJpaTemplate().getReference(Address.class, addressId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#findAddress(Integer)
     */
    @Override
    public Address findAddress(Integer addressId) {
        return getJpaTemplate().find(Address.class, addressId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#findAllAddresss()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Address> findAllAddresss() {
        return getJpaTemplate().find("from test.Address as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#updateAddress(Address)
     */
    @Override 
    public void updateAddress(Address address) {
        getJpaTemplate().merge(address);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#createAddress(Address)
     */
    @Override
    public void createAddress(Address address) {
        getJpaTemplate().persist(address);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.AddressDao#removeAddress(Integer)
     */
    @Override
    public void removeAddress(Integer addressId) {
         Address address = getJpaTemplate().getReference(Address.class, addressId);
         getJpaTemplate().remove(address);

    }

}
