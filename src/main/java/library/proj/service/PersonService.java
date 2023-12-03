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

    public List<Person> getAllUsers() {return personsRepository.findAll();}

    public Person getUser(int id) {
        return personsRepository.findById(id);
    }

    public Person getUser(String surname) {
        return personsRepository.findByLastName(surname);
    }

    public Person getUser(String email, String password) {
        return personsRepository.findByEmailAndPassword(email, password);
    }

    public Person createUser(Person person) {
        return personsRepository.save(person);
    }

}
