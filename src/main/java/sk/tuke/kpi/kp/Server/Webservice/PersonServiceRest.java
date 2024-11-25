package sk.tuke.kpi.kp.Server.Webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.Entity.Person;
import sk.tuke.kpi.kp.Service.PersonPackage.PersonService;
import sk.tuke.kpi.kp.Service.ScorePackage.ScoreService;
import sk.tuke.kpi.kp.Entity.Score;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonServiceRest {

    @Autowired
    private PersonService personService;
    @GetMapping("/get")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public void addPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }
}