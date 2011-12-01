package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.TypeDao;
import test.pack.data.greenvine.entity.test.Type;
import java.lang.Long;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class TypeDaoImpl extends HibernateDaoSupport implements TypeDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#loadType(Long)
     */
    @Override
    public Type loadType(Long type6) {
        return (Type) getHibernateTemplate().load(Type.class, type6);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#findType(Long)
     */
    @Override
    public Type findType(Long type6) {
        return (Type) getHibernateTemplate().get(Type.class, type6);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#findAllTypes()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Type> findAllTypes() {
        return (List<Type>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Type as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#updateType(Type)
     */
    @Override 
    public void updateType(Type type) {
        getHibernateTemplate().merge(type);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#createType(Type)
     */
    @Override
    public void createType(Type type) {
        getHibernateTemplate().persist(type);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#removeType(Long)
     */
    @Override
    public void removeType(Long type6) {
         Object type = getHibernateTemplate().load(Type.class, type6);
         getHibernateTemplate().delete(type);

    }

}
