package library.proj;

import javafx.application.Application;
import library.proj.gui.LibraryUI;
import library.proj.model.*;
import library.proj.service.BooksService;
import library.proj.service.PersonService;
import library.proj.service.RentalsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        Application.launch(LibraryUI.class, args);
    }

    @Bean
    public CommandLineRunner addExampleData(PersonService personService, BooksService booksService, RentalsService rentalsService) {
        return (args -> {
            personService.savePerson(new Person("Rzegorz", "Gogus", "abc@asd.pl", "password", Permissions.ADMIN));
            personService.savePerson(new Person("Baran", "Adamski", "baran@gmail.com", "password", Permissions.USER));

            addBookExamples(booksService, rentalsService, personService);
        });
    }

    private void addBookExamples(BooksService booksService, RentalsService rentalsService, PersonService personService) {

        booksService.createBook(new Book("W pustyni i w puszczy", "Henryk Sienkiewicz", "Twarda", "przygody Stasia i Nel", Status.AVAILABLE,"/binaries/w_pustyni_i_w_puszczy.jpg"));
        booksService.createBook(new Book("Harry Potter i Kamień Filozoficzny", "J.K. Rowling", "Miękka", "Pierwsza część przygód młodego czarodzieja", Status.AVAILABLE,"/binaries/kamien_filozoficzny.jpg"));
        booksService.createBook(new Book("Harry Potter i Komnata Tajemnic", "J.K. Rowling", "Miękka", "Druga część przygód młodego czarodzieja", Status.AVAILABLE,"/binaries/komnata_tajemnic.jpg"));
        booksService.createBook(new Book("Harry Potter i Więzień Azkabanu", "J.K. Rowling", "Miękka", "Trzecia część przygód młodego czarodzieja", Status.AVAILABLE,"/binaries/wiezien_azkabanu.jpg"));
        booksService.createBook(new Book("Harry Potter i Czara Ognia", "J.K. Rowling", "Miękka", "Czwarta część przygód młodego czarodzieja", Status.AVAILABLE,"/binaries/czara_ognia.jpg"));
        booksService.createBook(new Book("Harry Potter i Zakon Feniksa", "J.K. Rowling", "Miękka", "Piąta część przygód młodego czarodzieja", Status.AVAILABLE,"/binaries/zakon_feniksa.jpg"));
        booksService.createBook(new Book("Harry Potter i Książę Półkrwi", "J.K. Rowling", "Miękka", "Szósta część przygód młodego czarodzieja", Status.AVAILABLE,"/binaries/ksiaze_polkrwi.jpg"));
        Book insygnia=new Book("Harry Potter i Insygnia Śmierci", "J.K. Rowling", "Miękka", "Siódma część przygód młodego czarodzieja", Status.AVAILABLE,"/binaries/insygnia_smierci.jpg");
        booksService.createBook(insygnia);
    }

}
