package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.UserDao;
import test.pack.data.greenvine.entity.dbo.User;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class UserDaoImpl extends JpaDaoSupport implements UserDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#loadUser(Integer)
     */
    @Override
    public User loadUser(Integer userId) {
        return getJpaTemplate().getReference(User.class, userId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#findUser(Integer)
     */
    @Override
    public User findUser(Integer userId) {
        return getJpaTemplate().find(User.class, userId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#findAllUsers()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<User> findAllUsers() {
        return getJpaTemplate().find("from dbo.User as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#updateUser(User)
     */
    @Override 
    public void updateUser(User user) {
        getJpaTemplate().merge(user);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#createUser(User)
     */
    @Override
    public void createUser(User user) {
        getJpaTemplate().persist(user);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#removeUser(Integer)
     */
    @Override
    public void removeUser(Integer userId) {
         User user = getJpaTemplate().getReference(User.class, userId);
         getJpaTemplate().remove(user);

    }

}
