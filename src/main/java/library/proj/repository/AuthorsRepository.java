package library.proj.repository;

import library.proj.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    Author findById(int id);

    Author findByFirstNameAndLastName(String firstName, String lastName);
}
