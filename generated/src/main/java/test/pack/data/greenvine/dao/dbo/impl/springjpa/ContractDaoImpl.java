package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.ContractDao;
import test.pack.data.greenvine.entity.dbo.Contract;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class ContractDaoImpl extends JpaDaoSupport implements ContractDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#loadContract(Integer)
     */
    @Override
    public Contract loadContract(Integer employee) {
        return getJpaTemplate().getReference(Contract.class, employee);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#findContract(Integer)
     */
    @Override
    public Contract findContract(Integer employee) {
        return getJpaTemplate().find(Contract.class, employee);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#findAllContracts()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Contract> findAllContracts() {
        return getJpaTemplate().find("from dbo.Contract as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#updateContract(Contract)
     */
    @Override 
    public void updateContract(Contract contract) {
        getJpaTemplate().merge(contract);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#createContract(Contract)
     */
    @Override
    public void createContract(Contract contract) {
        getJpaTemplate().persist(contract);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.ContractDao#removeContract(Integer)
     */
    @Override
    public void removeContract(Integer employee) {
         Contract contract = getJpaTemplate().getReference(Contract.class, employee);
         getJpaTemplate().remove(contract);

    }

}
