package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.UserDao;
import test.pack.data.greenvine.entity.dbo.User;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#loadUser(Integer)
     */
    @Override
    public User loadUser(Integer userId) {
        return (User) getHibernateTemplate().load(User.class, userId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#findUser(Integer)
     */
    @Override
    public User findUser(Integer userId) {
        return (User) getHibernateTemplate().get(User.class, userId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#findAllUsers()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<User> findAllUsers() {
        return (List<User>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.dbo.User as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#updateUser(User)
     */
    @Override 
    public void updateUser(User user) {
        getHibernateTemplate().merge(user);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#createUser(User)
     */
    @Override
    public void createUser(User user) {
        getHibernateTemplate().persist(user);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UserDao#removeUser(Integer)
     */
    @Override
    public void removeUser(Integer userId) {
         Object user = getHibernateTemplate().load(User.class, userId);
         getHibernateTemplate().delete(user);

    }

}
