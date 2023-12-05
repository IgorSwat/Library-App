package library.proj.service;

import library.proj.model.Person;
import library.proj.repository.PersonsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonsRepository personsRepository;

    public PersonService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public List<Person> getAllPersons() {
        return personsRepository.findAll();
    }

    public Person getPerson(int id) {
        return personsRepository.findById(id);
    }

    public Person getPerson(String surname) {
        return personsRepository.findByLastName(surname);
    }

    public Person getPerson(String email, String password) {
        return personsRepository.findByEmailAndPassword(email, password);
    }

    public Person savePerson(Person person) {
        return personsRepository.save(person);
    }

    public boolean exists(String email) {
        return personsRepository.existsByEmail(email);
    }

}
