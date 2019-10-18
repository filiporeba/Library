package Library.repository;

import Library.model.Book;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookRepsository {

    Book book1 = new Book(1, "Tolkien", "Lord of the Rings");
    Book book2 = new Book(2, "Tolkien", "Hobbit");
    Book book3 = new Book(3, "Tolkien", "Hobbit 2");
    Book book4 = new Book(4, "Tolkien", "Hobbit 3");
    Book book5 = new Book(5, "Tolkien", "Lord of the Rings 2");
    Book book6 = new Book(6, "Tolkien", "Lord of the Rings 3");
    Book book7 = new Book(7, "Adam", "Superman");
    Book book8 = new Book(8, "Adam", "Superman 2");
    Book book9 = new Book(9, "J.K Rowling", "Harry");
    Book book10 = new Book(10, "J.K Rowling", "Potter");

    private Set<Book> booksDataBase;

    public BookRepsository() {
        booksDataBase = new HashSet<>(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10));
    }

    public Optional<Book> borrowBook(String title, LocalDate date) {

        Optional<Book> any = booksDataBase.stream().
                filter(book -> title.equals(book.getTitle())).
                filter(book -> book.getDate() == null).
                findAny();

        any.ifPresent(book -> book.setDate(date));

        return any;
    }

    public Book addBookToBase(String author, String title) {
        int lastIDinBase = booksDataBase.stream()
                .mapToInt(Book::getId)
                .max()
                .getAsInt();

        Book newBook = new Book(lastIDinBase + 1, author, title);

        booksDataBase.add(newBook);

        return newBook;
    }

    public void deleteBookFromBase(int id){
        Optional<Book> bookToDelete = booksDataBase.stream()
                .filter(book->book.getId() == id)
                .findAny();
        bookToDelete.ifPresent(book ->booksDataBase.remove(book));
    }

    public void returnBook(int id){
        booksDataBase.stream()
                .filter(book->book.getId() == id)
                .findAny()
                .orElseThrow(()->new RuntimeException("Book don't exist"))
                .setDate(null);
    }

    public Set<Book> getBookBase(){
        return booksDataBase;
    }
}
