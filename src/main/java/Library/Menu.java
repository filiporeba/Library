package Library;

import Library.model.Book;
import Library.repository.BookRepsository;
import Library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class Menu {

    OrderService orderService;

    @Autowired
    public Menu(OrderService orderService, BookRepsository repsository) {
        this.orderService = orderService;
    }

    public void displayFunction() {
        Scanner scanner = new Scanner(System.in);
        displayMenu();

        int choice = scanner.nextInt();

        if (choice >= 7 || choice <= 0) {
            System.out.println("Podaj poprawny numer!");
            displayFunction();
        } else {
            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    addBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    displayBooks();
                    break;
                case 6:
                    System.exit(0);
            }
        }
    }

    private void displayBooks() {
        orderService.getBookDataBase()
                .stream()
                .forEach(book -> System.out.println(book.toString()));
    }

    private void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id książki do skasowania");
        int bookIdToDelete = scanner.nextInt();

        orderService.deleteBook(bookIdToDelete);
    }

    private void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj autora książki");
        String authorOfTheNewBook = scanner.nextLine();
        System.out.println("Podaj tytuł książki");
        String titleOfTheNewBook = scanner.nextLine();

        orderService.addNewBook(authorOfTheNewBook, titleOfTheNewBook);
    }

    private void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id książki do oddania");
        int bookIdToReturn = scanner.nextInt();
        orderService.returnBook(bookIdToReturn);
    }

    private void borrowBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj tytuł książki");
        String title = scanner.nextLine();

        if(orderService.getBookDataBase()
            .stream()
            .filter(book -> book.getTitle().equals(title))
            .findAny()
            .get().getDate() != null) {

            System.out.println("Książka jest wypożyczona do dnia " + orderService.getBookDataBase()
                .stream()
                .filter(book -> book.getTitle().equals(title))
                .findAny()
                .get().getDate().toString());
        } else {
            Optional<Book> bookToBorrow = orderService.borrowBook(title);

            if(!bookToBorrow.isPresent()) {
                System.out.println("Nie ma takiej książki");
            } else {
                System.out.println("Wypożyczyłeś książke");
                System.out.println("Numer: " + bookToBorrow.get().getId());
                System.out.println("Autor: " + bookToBorrow.get().getAuthor());
                System.out.println("Tytul: " + bookToBorrow.get().getTitle());
            }
        }

    }


    private void displayMenu() {
        System.out.println("Co chcesz zrobić? - wybierz numer z menu! ");
        System.out.println("1. Wypożyczyć książkę");
        System.out.println("2. Oddać książkę");
        System.out.println("3. Dodać książkę");
        System.out.println("4. Usunąć książkę");
        System.out.println("5. Wyświetl pozycje");
        System.out.println("6. Zamknij program");
    }
}
