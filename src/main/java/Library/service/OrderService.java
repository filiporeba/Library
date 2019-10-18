package Library.service;

import Library.model.Book;
import Library.repository.BookRepsository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final BookRepsository repsository;

    @Autowired
    public OrderService(BookRepsository repsository) {
        this.repsository = repsository;
    }

    public Optional<Book> borrowBook(String title) {
        return repsository.borrowBook(title, LocalDate.now().plusDays(30));
    }

    public void returnBook (int bookId) {
        repsository.returnBook(bookId);
    }

    public void deleteBook (int bookId) {
        repsository.deleteBookFromBase(bookId);
    }

    public Book addNewBook (String author, String title) {
        return repsository.addBookToBase(author,title);
    }

    public Set<Book> getBookDataBase() {
        return repsository.getBookBase();
    }
}
