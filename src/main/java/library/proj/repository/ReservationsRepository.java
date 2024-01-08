package library.proj.repository;

import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Integer> {
    Reservation findById(int id);
    List<Reservation> findAllByPerson(Person person);
    List<Reservation> findAllByBook(Book book);

}
