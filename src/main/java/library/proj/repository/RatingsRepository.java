package library.proj.repository;

import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Rating, Integer> {
    Rating findById(int id);

    @Query("SELECT r FROM Rating r WHERE r.person = :person AND r.book = :book")
    Rating findByPersonAndBook(@Param("person") Person person, @Param("book") Book book);

    @Query("SELECT r FROM Rating r WHERE r.book = :book")
    List<Rating> findByBook(@Param("book") Book book);

    // Don know why but it requires some special treatment - otherwise it doesn't delete anything or crush happens :)
    @Transactional
    @Modifying
    @Query("DELETE FROM Rating r WHERE r.person = :person AND r.book = :book")
    void deleteByPersonAndBook(@Param("person") Person person, @Param("book") Book book);
}

