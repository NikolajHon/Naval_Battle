package sk.tuke.kpi.kp.Service.PersonPackage;

import sk.tuke.kpi.kp.Entity.Person;

import java.util.List;

public interface PersonService {
    void addPerson(Person person);

    List<Person> getAllPersons();
}
