package library.proj.repository;

import library.proj.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<Rating, Integer> {
    Rating findById(int id);
}
