package library.proj.repository;

import library.proj.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Person, Integer> {
    Person findById(int id);
    Person findByLastName(String lastName);
    Person findByEmail(String email);
    Person findByEmailAndPassword(String email, String password);
}
