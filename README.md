# Projekt TO - Czytelnia
## Nazwa zespołu: Hakuna Matata
### Autorzy: Hubert Obarzanek, Igor Swat, Krzysztof Dziechciarz, Mateusz Bywalec

---
## Opis projektu
Projekt polega na stworzeniu aplikacji wolnostojącej, która będzie symulować działanie czytelni. Projekt powinien zawierać poniższe punkty:
- Wprowadzanie nowego użytkownika (imię, nazwisko, email) oraz sprawdzenie poprawności danych
- Polecane książki
- Widok informacji o książce, w tym okładka i spis treści
- Różne usprawnienia, min. 3 (będą doprecyzowane w trakcie trwania projektu)
- System wysyłania maili z powiadomieniami
- Statystyki - graficzne

## Technologie
Język programowania: Java\
Framework: Spring\
Budowa projkektu: Gradle

---
# 1. Wprowadzanie nowego użytkownika (imię, nazwisko, email) oraz sprawdzenie poprawności danych
Wprowadzanie nowego użytkownika odbywa się za pomocą funkcji *invoke()* klasy *PersonAdder* z pakietu *library.proj.CLI*.\
Funkcja ta zczytuje z konsoli dane nowego użytkownika, sprawdza czy hasło jest wystarczająco długie (min. 8 znaków) oraz czy podany email jest poprawny (za pomocą regexu).\
W przypadku niepoprawnych danych, użytkownik jest proszony o ponowne wprowadzenie danych.\
Jeśli dane są poprawne, nowy użytkownik zostaje dodany do bazy za pomocą funkcji *savePerson()* klasy *PersonService* z pakietu *library.proj.service*.

# 2. Model bazodanowy
![Model bazodanowy](schemat%20bazy.png)

## Tabele
### Ratings
Tabela *Ratings* zawiera oceny książek wystawione przez użytkowników.\
Każda ocena jest powiązana z konkretną książką oraz użytkownikiem.

### Books
Tabela *Books* zawiera informacje o książkach.

### Users
Tabela *Users* zawiera informacje o użytkownikach.

### Rentals
Tabela *Rentals* zawiera informacje o wypożyczeniach książek przez użytkowników.\
Każde wypożyczenie jest powiązane z konkretną książką oraz użytkownikiem.

# 3. Model obiektowy
## 1.  Pakiet: *library.proj*
### 1.1 LibraryApplication
- **Anotacje**:
  - *@SpringBootApplication*: Oznacza klasę jako punkt startowy aplikacji Spring Boot, automatycznie konfigurując kontekst aplikacji.
  - *@Bean*: Oznacza metodę jako źródło zarządzanego przez Springa komponentu.
- **Metody**:
  - **main(String[] args)**: Metoda uruchamiająca aplikację. Wywołuje metodę launch z klasy Application, rozpoczynając działanie interfejsu użytkownika zaimplementowanego w klasie LibraryUI.
  - **addExampleData(PersonService personService, BooksService booksService)**: Metoda dodająca przykładowe dane do bazy danych przy starcie aplikacji. Korzysta z serwisów PersonService i BooksService do zapisu przykładowych osób oraz książek.
  - **addBookExamples(BooksService booksService, int noExamples)**: Prywatna metoda pomocnicza, dodająca przykładowe książki do bazy danych. Wykorzystuje serwis BooksService do stworzenia i zapisu książek.

## 2. Pakiet: *library.proj.service*
### 2.1 RentalsService
- **Anotacje**:
    - *@Service*: Oznacza klasę jako komponent serwisowy, umożliwiający Springowi zarządzanie nią.
- **Pola**:
    - *private final RentalsRepository rentalsRepository*: Repozytorium (RentalsRepository) służące do komunikacji z bazą danych w kontekście wypożyczeń.
- **Metody**:
    - *getAllRentals()*: Zwraca listę wszystkich wypożyczeń z bazy danych.
    - *getRentalsByUser(Person person)*: Zwraca listę wypożyczeń przypisanych do określonej osoby.
    - *getNotReturnedBooks()*: Zwraca listę wypożyczeń, które nie zostały jeszcze zwrócone.
    - *createRental(Rental rental)*: Tworzy nowe wypożyczenie i zapisuje je w bazie danych.

### 2.2 BooksService
- **Anotacje**:
    - *@Service*: Oznacza klasę jako komponent serwisowy, umożliwiający Springowi zarządzanie nią.
- **Pola**:
    - *private final BooksRepository booksRepository*: Repozytorium (BooksRepository) służące do komunikacji z bazą danych w kontekście książek.
- **Metody**:
    - *getAllBooks()*: Zwraca listę wszystkich książek z bazy danych.
    - *getAvailableBooks()*: Zwraca listę dostępnych książek.
    - *getBook(int id)*: Zwraca książkę o określonym identyfikatorze.
    - *getBooksByTitle(String title)*: Zwraca listę książek o określonym tytule.
    - *createBook(Book book)*: Tworzy nową książkę i zapisuje ją w bazie danych.

### 2.3 PersonService
- **Anotacje**:
    - *@Service*: Oznacza klasę jako komponent serwisowy, umożliwiający Springowi zarządzanie nią.
- **Pola**:
    - *private final PersonRepository personRepository*: Repozytorium (PersonRepository) służące do komunikacji z bazą danych w kontekście osób.
- **Metody**:
    - *getAllPersons()*: Zwraca listę wszystkich osób z bazy danych.
    - *getPerson(int id)*: Zwraca osobę o określonym identyfikatorze.
    - *getPerson(String surname)*: Zwraca osobę na podstawie nazwiska.
    - *getPerson(String email, String password)*: Zwraca osobę na podstawie adresu email i hasła.
    - *savePerson(Person person)*: Zapisuje nową osobę w bazie danych.
    - *exists(String email)*: Sprawdza, czy osoba o podanym adresie email istnieje w bazie danych.

### 2.4 RatingsService
- **Anotacje**:
    - *@Service*: Oznacza klasę jako komponent serwisowy, umożliwiający Springowi zarządzanie nią.
- **Pola**:
    - *private final RatingsRepository ratingsRepository*: Repozytorium (RatingsRepository) służące do komunikacji z bazą danych w kontekście ocen.
- **Metody**:
    - *getAllRatings()*: Zwraca listę wszystkich ocen z bazy danych.
    - *getRatingsByBook(Book book)*: Zwraca listę ocen przypisanych do określonej książki.
    - *getRatingsByUser(Person person)*: Zwraca listę ocen przypisanych do określonej osoby.
    - *createRating(Rating rating)*: Tworzy nową ocenę i zapisuje ją w bazie danych.  

## 3. Pakiet: *library.proj.repository*
Pakiet zawiera interfejsy repozytoriów, które służą do komunikacji z bazą danych. Wszystkie repozytoria dziedziczą po interfejsie JpaRepository, który zawiera podstawowe metody do komunikacji z bazą danych.
### 3.1 RentalsRepository
- **Anotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje wypożyczenie o określonym identyfikatorze.

### 3.2 BooksRepository
- **Anotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje książkę o określonym identyfikatorze.
    - *findByTitleAndAuthor*(String title, String author): Znajduje książkę na podstawie tytułu i autora.

### 3.3 PersonRepository
- **Anotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje osobę o określonym identyfikatorze.
    - *findByLastName(String lastName)*: Znajduje osobę na podstawie nazwiska.
    - *findByEmail(String email)*: Znajduje osobę na podstawie adresu email.
    - *findByEmailAndPassword(String email, String password)*: Znajduje osobę na podstawie adresu email i hasła.
    - *existsByEmail(String email)*: Sprawdza, czy osoba o podanym adresie email istnieje.

### 3.4 RatingsRepository
- **Anotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje ocenę o określonym identyfikatorze.

## 4. Pakiet: *library.proj.model*
### 4.1 Book
- **Anotacje**:
    - *@Entity*: Oznacza klasę jako encję JPA, co umożliwia mapowanie obiektów tej klasy na rekordy w bazie danych.
    - *@Id*: Oznacza pole id jako identyfikator encji.
    - *@GeneratedValue*: Określa strategię generowania wartości dla pola id.
    - *@OneToMany*: Określa relację jeden-do-wielu między książką a wypożyczeniami oraz ocenami.
    - *@Getter*: Generuje automatycznie metody dostępu do pól.
- **Pola**:
    - *private int id*: Unikalny identyfikator książki.
    - *private String title*: Tytuł książki.
    - *private String author*: Autor książki.
    - *private String cover*: Okładka książki.
    - *private String contents*: Spis treści książki.
    - *private int status*: Status dostępności książki (enum Status).
    - *private int views*: Liczba wyświetleń książki.
    - *private List<Rental> rentals*: Lista wypożyczeń związanych z książką.
    - *private List<Rating> ratings*: Lista ocen związanych z książką.
- **Konstruktory**:
    - *public Book()*: Konstruktor bezargumentowy.
    - *public Book(String title, String author, String cover, String contents, int status)*: Konstruktor inicjalizujący pola klasy.
- **Metody**:
    - *public void updateViews(int views)*: Aktualizuje liczbę wyświetleń książki.
    - *public void registerRental(Rental rental)*: Rejestruje nowe wypożyczenie związane z książką.
    - *public void addRating(Rating rating)*: Dodaje nową ocenę związana z książką.
    - *public boolean isAvailable()*: Sprawdza dostępność książki.

### 4.2 Status
- **Pola**:
    - *AVAILABLE(0)*: Oznacza dostępność książki.
    - *NOT_AVAILABLE(1)*: Oznacza niedostępność książki.
- **Metody**:
    - *Status(int status)*: Konstruktor przypisujący wartość polu status.
    - *public String toString()*: Zwraca czytelny opis statusu.

### 4.3 Rental
- **Anotacje**:
    - *@Entity*: Oznacza klasę jako encję JPA.
    - *@Id*: Oznacza pole id jako identyfikator encji.
    - *@GeneratedValue*: Określa strategię generowania wartości dla pola id.
    - *@ManyToOne*: Określa relację wiele-do-jednego między wypożyczeniem a osobą (klasa Person) oraz między wypożyczeniem a książką (klasa Book).
    - *@JoinColumn*: Określa kolumnę, która jest kluczem obcym w relacji.
- **Pola**:
    - *private int id*: Unikalny identyfikator wypożyczenia.
    - *private Person person*: Osoba dokonująca wypożyczenia.
    - *private Book book*: Książka wypożyczona.
    - *private Date rentalDate*: Data wypożyczenia.
    - *private Date returnDate*: Data planowanego zwrotu.
- **Konstruktory**:
    - *public Rental()*: Konstruktor domyślny.
    - *public Rental(Person person, Book book, Date rentalDate)*: Konstruktor inicjalizujący pola klasy oraz dodający wypożyczenie do listy wypożyczeń osoby i książki.

### 4.4 Rating
- **Anotacje**:
    - *@Entity*: Oznacza klasę jako encję JPA.
    - *@Id*: Oznacza pole id jako identyfikator encji.
    - *@GeneratedValue*: Określa strategię generowania wartości dla pola id.
    - *@ManyToOne*: Określa relację wiele-do-jednego między oceną a osobą (klasa Person) oraz między oceną a książką (klasa Book).
    - *@JoinColumn*: Określa kolumnę, która jest kluczem obcym w relacji.
- **Pola**:
    - *private int id*: Unikalny identyfikator oceny.
    - *private Person person*: Osoba dokonująca oceny.
    - *private Book book*: Książka oceniana.
    - *private int rating*: Wartość oceny.
- **Konstruktory**:
    - *public Rating()*: Konstruktor domyślny.
    - *public Rating(Person person, Book book, int rating)*: Konstruktor inicjalizujący pola klasy oraz dodający ocenę do listy ocen osoby i książki.

### 4.5 Person
- **Anotacje**:
    - *@Entity*: Oznacza klasę jako encję JPA.
    - *@Id*: Oznacza pole id jako identyfikator encji.
    - *@GeneratedValue*: Określa strategię generowania wartości dla pola id.
    - *@OneToMany*: Określa relację jeden-do-wielu między osobą a wypożyczeniami oraz ocenami.
    - *@Getter*: Generuje automatycznie metody dostępu do pól.
- **Pola**:
    - *private int id*: Unikalny identyfikator osoby.
    - *private String firstName*: Imię osoby.
    - *private String lastName*: Nazwisko osoby.
    - *private String email*: Adres email osoby (unikalny).
    - *private String password*: Hasło osoby.
    - *private int permissions*: Uprawnienia osoby (enum Permissions).
    - *private List<Rental> rentals*: Lista wypożyczeń związanych z osobą.
    - *private List<Rating> ratings*: Lista ocen związanych z osobą.
- **Konstruktory**:
    - *public Person()*: Konstruktor domyślny.
    - *public Person(String firstName, String lastName, String email, String password, Permissions permissions)*: Konstruktor inicjalizujący pola klasy.
- **Metody**:
    - *public String toString()*: Zwraca czytelny opis osoby.
    - *public void registerRental(Rental rental)*: Rejestruje nowe wypożyczenie związane z osobą.
    - *public void addRating(Rating rating)*: Dodaje nową ocenę związana z osobą.

### 4.6 Permissions
- **Pola**:
    - *ADMIN(0)*: Oznacza administratora systemu.
    - *EMPLOYEE(1)*: Oznacza pracownika systemu.
    - *USER(2)*: Oznacza standardowego użytkownika systemu.
- **Metody**:
    - *Permissions(int permissions)*: Konstruktor przypisujący wartość polu permissions.
    - *public static Permissions parse(int value)*: Parsuje wartość liczbową na odpowiadający poziom uprawnień.
    - *public String toString()*: Zwraca czytelny opis poziomu uprawnień.

## 5. Pakiet: *library.proj.gui*

## 6. Pakiet: *library.proj.gui.controllers*

## 7. Pakiet: *library.proj.gui.events*

## 8. Pakiet: *library.proj.gui.scenes*

## 9. Pakiet: *library.proj.gui.scenes.objects*