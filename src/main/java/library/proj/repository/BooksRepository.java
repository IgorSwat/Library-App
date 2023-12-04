package library.proj.repository;

import library.proj.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Book findById(int id);

    Book findByTitleAndAuthor(String title, String author);
}
