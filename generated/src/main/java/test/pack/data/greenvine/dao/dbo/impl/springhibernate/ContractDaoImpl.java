package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.ContractDao;
import test.pack.data.greenvine.entity.dbo.Contract;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class ContractDaoImpl extends HibernateDaoSupport implements ContractDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#loadContract(Integer)
     */
    @Override
    public Contract loadContract(Integer employee) {
        return (Contract) getHibernateTemplate().load(Contract.class, employee);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#findContract(Integer)
     */
    @Override
    public Contract findContract(Integer employee) {
        return (Contract) getHibernateTemplate().get(Contract.class, employee);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#findAllContracts()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Contract> findAllContracts() {
        return (List<Contract>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.dbo.Contract as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#updateContract(Contract)
     */
    @Override 
    public void updateContract(Contract contract) {
        getHibernateTemplate().merge(contract);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#createContract(Contract)
     */
    @Override
    public void createContract(Contract contract) {
        getHibernateTemplate().persist(contract);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#removeContract(Integer)
     */
    @Override
    public void removeContract(Integer employee) {
         Object contract = getHibernateTemplate().load(Contract.class, employee);
         getHibernateTemplate().delete(contract);

    }

}
