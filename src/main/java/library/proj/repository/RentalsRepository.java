package library.proj.repository;

import library.proj.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalsRepository extends JpaRepository<Rental, Integer> {

    Rental findById(int id);
}
