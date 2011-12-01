package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.TypeDao;
import test.pack.data.greenvine.entity.test.Type;
import java.lang.Long;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class TypeDaoImpl extends JpaDaoSupport implements TypeDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#loadType(Long)
     */
    @Override
    public Type loadType(Long type6) {
        return getJpaTemplate().getReference(Type.class, type6);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#findType(Long)
     */
    @Override
    public Type findType(Long type6) {
        return getJpaTemplate().find(Type.class, type6);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#findAllTypes()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Type> findAllTypes() {
        return getJpaTemplate().find("from test.Type as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#updateType(Type)
     */
    @Override 
    public void updateType(Type type) {
        getJpaTemplate().merge(type);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#createType(Type)
     */
    @Override
    public void createType(Type type) {
        getJpaTemplate().persist(type);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.TypeDao#removeType(Long)
     */
    @Override
    public void removeType(Long type6) {
         Type type = getJpaTemplate().getReference(Type.class, type6);
         getJpaTemplate().remove(type);

    }

}
