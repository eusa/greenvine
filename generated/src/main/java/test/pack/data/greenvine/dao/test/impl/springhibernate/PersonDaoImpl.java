package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.PersonDao;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class PersonDaoImpl extends HibernateDaoSupport implements PersonDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#loadPerson(PersonIdentity)
     */
    @Override
    public Person loadPerson(PersonIdentity personIdentity) {
        return (Person) getHibernateTemplate().load(Person.class, personIdentity);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#findPerson(PersonIdentity)
     */
    @Override
    public Person findPerson(PersonIdentity personIdentity) {
        return (Person) getHibernateTemplate().get(Person.class, personIdentity);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#findAllPersons()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Person> findAllPersons() {
        return (List<Person>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Person as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#updatePerson(Person)
     */
    @Override 
    public void updatePerson(Person person) {
        getHibernateTemplate().merge(person);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#createPerson(Person)
     */
    @Override
    public void createPerson(Person person) {
        getHibernateTemplate().persist(person);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#removePerson(PersonIdentity)
     */
    @Override
    public void removePerson(PersonIdentity personIdentity) {
         Object person = getHibernateTemplate().load(Person.class, personIdentity);
         getHibernateTemplate().delete(person);

    }

}
