package sk.tuke.kpi.kp.Service.PersonPackage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import sk.tuke.kpi.kp.Entity.Person;

import java.util.List;

@Component
@Transactional
public class PersonServiceJPA implements PersonService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addPerson(Person person) {
        try {
            Person existingPlayer = entityManager.createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class)
                    .setParameter("name", person.getName())
                    .getSingleResult();

            // Если игрок с таким именем уже существует, выбрасываем исключение
            throw new PlayerExistsException("Player with name '" + person.getName() + "' already exists");
        } catch (NoResultException e) {
            // Если игрок с таким именем не существует, добавляем нового игрока
            entityManager.persist(person);
        } catch (PlayerExistsException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Person> getAllPersons() {
        return entityManager.createQuery("SELECT p FROM Person p", Person.class)
                .getResultList();
    }

}
