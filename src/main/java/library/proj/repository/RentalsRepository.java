package library.proj.repository;

import library.proj.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalsRepository extends JpaRepository<Rental, Integer> {
    Rental findById(int id);
}
