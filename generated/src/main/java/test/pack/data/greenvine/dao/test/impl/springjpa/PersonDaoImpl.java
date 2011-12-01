package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.PersonDao;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonIdentity;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class PersonDaoImpl extends JpaDaoSupport implements PersonDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#loadPerson(PersonIdentity)
     */
    @Override
    public Person loadPerson(PersonIdentity personIdentity) {
        return getJpaTemplate().getReference(Person.class, personIdentity);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#findPerson(PersonIdentity)
     */
    @Override
    public Person findPerson(PersonIdentity personIdentity) {
        return getJpaTemplate().find(Person.class, personIdentity);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#findAllPersons()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Person> findAllPersons() {
        return getJpaTemplate().find("from test.Person as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#updatePerson(Person)
     */
    @Override 
    public void updatePerson(Person person) {
        getJpaTemplate().merge(person);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#createPerson(Person)
     */
    @Override
    public void createPerson(Person person) {
        getJpaTemplate().persist(person);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.PersonDao#removePerson(PersonIdentity)
     */
    @Override
    public void removePerson(PersonIdentity personIdentity) {
         Person person = getJpaTemplate().getReference(Person.class, personIdentity);
         getJpaTemplate().remove(person);

    }

}
