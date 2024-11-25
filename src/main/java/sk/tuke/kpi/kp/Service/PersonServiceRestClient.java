package sk.tuke.kpi.kp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.Entity.Person;
import sk.tuke.kpi.kp.Service.PersonPackage.PersonService;

import java.util.Arrays;
import java.util.List;

@Component
public class PersonServiceRestClient implements PersonService {
    private final String url = "http://localhost:8080/api/person";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addPerson(Person person) {
        restTemplate.postForEntity(url, person, Person.class);
    }

    @Override
    public List<Person> getAllPersons() {
        return Arrays.asList(restTemplate.getForEntity(url, Person[].class).getBody());
    }
}
