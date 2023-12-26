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

## M1 - TODO
- [x] Wprowadzanie nowego użytkownika (imię, nazwisko, email) oraz sprawdzenie poprawności danych
- [x] Model bazodanowy
- [x] Model obiektowy

## M2 - TODO
- [x] Lista książek
- [x] Widok osobnej książki(klikamy w książkę i wyświetlają się szczegóły)
- [x] Wypożyczanie książki
- [x] Widok naszych wypożyczeń(aktualne wypożyczenia)
- [x] Panel bibliotekarza(kto jakie ma wypożyczenia, muszą być wszystkie uprawnienia zaimplementowane)
- [x] Formularz dodawania nowej książki(przez bibliotekarza)

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
- **Adnotacje**:
  - *@SpringBootApplication*: Oznacza klasę jako punkt startowy aplikacji Spring Boot, automatycznie konfigurując kontekst aplikacji.
  - *@Bean*: Oznacza metodę jako źródło zarządzanego przez Springa komponentu.
- **Metody**:
  - **main(String[] args)**: Metoda uruchamiająca aplikację. Wywołuje metodę launch z klasy Application, rozpoczynając działanie interfejsu użytkownika zaimplementowanego w klasie LibraryUI.
  - **addExampleData(PersonService personService, BooksService booksService)**: Metoda dodająca przykładowe dane do bazy danych przy starcie aplikacji. Korzysta z serwisów PersonService i BooksService do zapisu przykładowych osób oraz książek.
  - **addBookExamples(BooksService booksService, int noExamples)**: Prywatna metoda pomocnicza, dodająca przykładowe książki do bazy danych. Wykorzystuje serwis BooksService do stworzenia i zapisu książek.

## 2. Pakiet: *library.proj.service*
### 2.1 RentalsService
- **Adnotacje**:
    - *@Service*: Oznacza klasę jako komponent serwisowy, umożliwiający Springowi zarządzanie nią.
- **Pola**:
    - *private final RentalsRepository rentalsRepository*: Repozytorium (RentalsRepository) służące do komunikacji z bazą danych w kontekście wypożyczeń.
- **Metody**:
    - *getAllRentals()*: Zwraca listę wszystkich wypożyczeń z bazy danych.
    - *getRentalsByUser(Person person)*: Zwraca listę wypożyczeń przypisanych do określonej osoby.
    - *getNotReturnedBooks()*: Zwraca listę wypożyczeń, które nie zostały jeszcze zwrócone.
    - *createRental(Rental rental)*: Tworzy nowe wypożyczenie i zapisuje je w bazie danych.

### 2.2 BooksService
- **Adnotacje**:
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
- **Adnotacje**:
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
- **Adnotacje**:
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
- **Adnotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje wypożyczenie o określonym identyfikatorze.

### 3.2 BooksRepository
- **Adnotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje książkę o określonym identyfikatorze.
    - *findByTitleAndAuthor*(String title, String author): Znajduje książkę na podstawie tytułu i autora.

### 3.3 PersonRepository
- **Adnotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje osobę o określonym identyfikatorze.
    - *findByLastName(String lastName)*: Znajduje osobę na podstawie nazwiska.
    - *findByEmail(String email)*: Znajduje osobę na podstawie adresu email.
    - *findByEmailAndPassword(String email, String password)*: Znajduje osobę na podstawie adresu email i hasła.
    - *existsByEmail(String email)*: Sprawdza, czy osoba o podanym adresie email istnieje.

### 3.4 RatingsRepository
- **Adnotacje**:
    - *@Repository*: Oznacza klasę jako komponent repozytorium, umożliwiający Springowi zarządzanie nią.
- **Metody**:
    - *findById(int id)*: Znajduje ocenę o określonym identyfikatorze.

## 4. Pakiet: *library.proj.model*
### 4.1 Book
- **Adnotacje**:
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
- **Adnotacje**:
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
- **Adnotacje**:
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
- **Adnotacje**:
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
### 5.1 LibraryUI
Klasa LibraryUI dziedziczy po klasie Application z JavaFX, co wskazuje, że jest to główna klasa aplikacji JavaFX.
- **Pola**:
    - *private ConfigurableApplicationContext applicationContext*: Pole przechowujące kontekst aplikacji Spring.
- **Metody**:
    - *public void init()*: Metoda inicjalizująca aplikację, uruchamiająca kontekst Spring.
    - *public void start(Stage stage)*: Metoda startująca aplikację, wywołująca zmianę sceny na scenę logowania.
    - *public void stop()*: Metoda zamykająca kontekst Spring i zamykająca platformę JavaFX.

## 6. Pakiet: *library.proj.gui.controllers*
Każdy plik kontrolera odpowiada za obsługę interakcji użytkownika w danym widoku.
W każdej klasie wykorzystywane są adnotacje @FXML do wstrzykiwania elementów interfejsu użytkownika (np. pola tekstowe, przyciski).
### 6.1 AddBookController
- **Pola**:
    - *private final Stage primaryStage*: Przechowuje referencję do głównego okna aplikacji.
    - *private final Stage stage*: Przechowuje referencję do okna kontrolera.
    - *private final ConfigurableApplicationContext context*: Przechowuje kontekst aplikacji Spring.
    - *private final BooksService booksService*: Serwis do obsługi operacji na książkach.
    - *@FXML private TextField titleField*: Pole do wprowadzania tytułu nowej książki.
    - *@FXML private TextField authorField*: Pole do wprowadzania autora nowej książki.
    - *@FXML private TextArea descriptionField*: Pole do wprowadzania opisu nowej książki.
    - *@FXML private ToggleGroup toggleGroup*: Grupa przycisków do wyboru statusu książki (dostępna/niedostępna).
    - *@FXML private Label errorLabel*: Etykieta do wyświetlania komunikatów błędu.
- **Metody**:
    - *public AddBookController(Stage primaryStage, Stage stage, ConfigurableApplicationContext context)*: Konstruktor, inicjalizuje pola.
    - *@FXML public void handleAddClick()*: Obsługuje zdarzenie dodania nowej książki.
    - *private boolean validateInput(String title, String author)*: Sprawdza poprawność wprowadzonych danych.

### 6.2 BookDetailsController
- **Pola**:
    - *private Book book*: Przechowuje informacje o książce.
    - *private Stage stage*: Przechowuje referencję do okna kontrolera.
    - *private ConfigurableApplicationContext context*: Przechowuje kontekst aplikacji Spring.
    - *private SceneCreator previousScene*: Przechowuje poprzednią scenę.
    - *@FXML public Label bookTitleField*: Etykieta do wyświetlania tytułu książki.
    - *@FXML public Label authorField*: Etykieta do wyświetlania autora książki.
    - *@FXML public Label coverField*: Etykieta do wyświetlania okładki książki.
    - *@FXML public Label contentField*: Etykieta do wyświetlania treści książki.
    - *@FXML public Label statusField*: Etykieta do wyświetlania statusu dostępności książki.
    - *@FXML private ImageView imageViewField*: Pole do wyświetlania obrazu okładki książki.
- **Metody**:
    - *public BookDetailsController(Stage stage, ConfigurableApplicationContext context, Book book, SceneCreator previousScene)*: Konstruktor, inicjalizuje pola.
    - *public void setFields()*: Ustawia wartości pól etykiet na podstawie informacji o książce.

### 6.3 BookListController
- **Pola**:
    - *private final Stage stage*: Przechowuje referencję do głównego okna aplikacji.
    - *private final ConfigurableApplicationContext context*: Przechowuje kontekst aplikacji Spring.
    - *private final BooksService booksService*: Serwis do obsługi operacji na książkach.
    - *@FXML private VBox bookList*: Kontener do wyświetlania listy książek.
    - *@FXML private Button addBookButton*: Przycisk do dodawania nowej książki.
    - *private static final int maxEntriesInRow*: Maksymalna liczba książek w jednym rzędzie.
    - *private static final double entriesSpacing*: Odległość między pozycjami książek.
- **Metody**:
    - *public BookListController(Stage stage, ConfigurableApplicationContext context)*: Konstruktor, inicjalizuje pola.
    - *public void updateBookList()*: Aktualizuje listę książek w interfejsie użytkownika.
    - *public void updateNavbar()*: Aktualizuje pasek nawigacyjny w zależności od uprawnień użytkownika.
    - *@FXML public void handleAddBookClick()*: Obsługuje zdarzenie dodania nowej książki.
    - *public void handleBookDetailsClicked(MouseEvent event, Book book)*: Obsługuje kliknięcie na pozycję z listy książek.
    - *public void handleUserClicked()*: Obsługuje kliknięcie na użytkownika.

### 6.4 LoginController
- **Pola**:
    - *private final Stage stage*: Przechowuje referencję do głównego okna aplikacji.
    - *private final ConfigurableApplicationContext context*: Przechowuje kontekst aplikacji Spring.
    - *private final PersonService personService*: Serwis do obsługi operacji na osobach.
    - *@FXML private TextField emailField*: Pole do wprowadzania adresu e-mail.
    - *@FXML private PasswordField passwordField*: Pole do wprowadzania hasła.
    - *@FXML private Label errorLabel*: Etykieta do wyświetlania komunikatów błędu.
    - *public static Person loggedAccount = null*: Statyczne pole przechowujące zalogowanego użytkownika.
- **Metody**:
    - *public LoginController(Stage stage, ConfigurableApplicationContext context)*: Konstruktor, inicjalizuje pola.
    - *@FXML public void handleLogin()*: Obsługuje zdarzenie logowania.
    - *@FXML public void handleRegisterRedirect()*: Obsługuje przekierowanie do ekranu rejestracji.
    - *private boolean validateInput(String email, String password)*: Sprawdza poprawność wprowadzonych danych.

### 6.5 MyRentalsController
- **Pola**:
    - *private final Stage stage*: Przechowuje referencję do głównego okna aplikacji.
    - *private final ConfigurableApplicationContext context*: Przechowuje kontekst aplikacji Spring.
    - *private final RentalsService rentalsService*: Serwis do obsługi operacji na wypożyczeniach.
    - *@FXML private VBox vBoxContainer*: Kontener do wyświetlania listy wypożyczonych książek.
- **Metody**:
    - *public MyRentalsController(Stage stage, ConfigurableApplicationContext context)*: Konstruktor, inicjalizuje pola.
    - *@FXML private void handleBackButton()*: Obsługuje przycisk powrotu.
    - *public void fillContainer()*: Wypełnia kontener listą wypożyczonych książek.
    - *public void handleBookDetailsClicked(MouseEvent event, Book book)*: Obsługuje kliknięcie na pozycję z listy książek.

### 6.6 RegisterController
- **Pola**:
    - *private final Stage stage*: Przechowuje referencję do głównego okna aplikacji.
    - *private final ConfigurableApplicationContext context*: Przechowuje kontekst aplikacji Spring.
    - *private final PersonService personService*: Serwis do obsługi operacji na osobach.
    - *@FXML private TextField nameField*: Pole do wprowadzania imienia.
    - *@FXML private TextField surnameField*: Pole do wprowadzania nazwiska.
    - *@FXML private TextField emailField*: Pole do wprowadzania adresu e-mail.
    - *@FXML private PasswordField passwordField*: Pole do wprowadzania hasła.
    - *@FXML private PasswordField repeatPasswordField*: Pole do wprowadzania powtórzonego hasła.
    - *@FXML private Label errorLabel*: Etykieta do wyświetlania komunikatów błędu.
- **Metody**:
    - *public RegisterController(Stage stage, ConfigurableApplicationContext context)*: Konstruktor, inicjalizuje pola.
    - *@FXML public void handleRegister()*: Obsługuje zdarzenie rejestracji.
    - *@FXML public void handleLoginRedirect()*: Obsługuje przekierowanie do ekranu logowania.
    - *private boolean validateInput(String name, String surname, String email, String password, String repeatPassword)*: Sprawdza poprawność wprowadzonych danych.
    - *private boolean isEmailValid(String email)*: Sprawdza poprawność formatu adresu e-mail.
    - *private boolean isPasswordValid(String password)*: Sprawdza poprawność długości hasła.

### 6.7 RentBookController
- **Pola**:
    - *private final Stage primaryStage*: Przechowuje referencję do głównego okna aplikacji.
    - *private final Stage stage*: Przechowuje referencję do okna kontrolera.
    - *private final ConfigurableApplicationContext context*: Przechowuje kontekst aplikacji Spring.
    - *private final BooksService booksService*: Serwis do obsługi operacji na książkach.
    - *private final RentalsService rentalsService*: Serwis do obsługi operacji na wypożyczeniach.
    - *private final PersonService personService*: Serwis do obsługi operacji na osobach.
    - *private final Book book*: Przechowuje informacje o wypożyczanej książce.
    - *@FXML private Label titleLabel*: Etykieta do wyświetlania tytułu książki.
    - *@FXML private Label authorLabel*: Etykieta do wyświetlania autora książki.
    - *@FXML ImageView coverImage*: Pole do wyświetlania obrazu okładki książki.
    - *@FXML TextField customerEmail*: Pole do wprowadzania adresu e-mail klienta.
    - *@FXML DatePicker startDate*: Pole do wybierania daty wypożyczenia.
    - *@FXML Label errorLabel*: Etykieta do wyświetlania komunikatów błędu.
    - *@FXML Label availabilityLabel*: Etykieta do wyświetlania dostępności książki.
- **Metody**:
    - *@FXML public void handleRentClick()*: Obsługuje zdarzenie wypożyczenia książki.
    - *private boolean validateHireRequirements(Person person, LocalDate date)*: Sprawdza poprawność wprowadzonych danych do wypożyczenia.
    - *public void initialize()*: Inicjalizuje kontroler, ustawiając wartości pól na podstawie informacji o książce.

## 7. Pakiet: *library.proj.gui.events*
Obie klasy dziedziczą po klasie ApplicationEvent z Springa
### 7.1 ChangeSceneEvent
- **Adnotacje**:
    - *@Getter*: Generuje automatycznie metody dostępu do pól.
- **Pola**:
    - *@Getter private final ConfigurableApplicationContext context*: Kontekst aplikacji Spring, przechowuje informacje o kontekście aplikacji.
    - *@Getter private final SceneCreatorIf sceneCreatorIf*: Interfejs SceneCreatorIf, przechowuje informacje o twórcy sceny.
- **Metody**:
    - *public ChangeSceneEvent(Stage stage, ConfigurableApplicationContext context, SceneCreatorIf creator)*: Konstruktor, inicjalizuje pola klasy.
    - *public Stage getStage()*: Zwraca obiekt Stage zdarzenia.

### 7.2 OpenDialogEvent
- **Adnotacje**:
    - *@Getter*: Generuje automatycznie metody dostępu do pól.
- **Pola**:
    - *@Getter private final String dialogName*: Nazwa okna dialogowego, przechowuje informacje o nazwie okna.
    - *@Getter private final int dialogWidth*: Szerokość okna dialogowego, przechowuje informacje o szerokości okna.
    - *@Getter private final int dialogHeight*: Wysokość okna dialogowego, przechowuje informacje o wysokości okna.
    - *@Getter private final ConfigurableApplicationContext context*: Kontekst aplikacji Spring, przechowuje informacje o kontekście aplikacji.
    - *@Getter private final SceneCreatorIf sceneCreatorIf*: Interfejs SceneCreatorIf, przechowuje informacje o twórcy sceny.
- **Metody**:
    - *public OpenDialogEvent(String dialogName, int dialogWidth, int dialogHeight, Stage primaryStage, ConfigurableApplicationContext context, SceneCreatorIf creator)*: Konstruktor, inicjalizuje pola klasy.
    - *public Stage getStage()*: Zwraca obiekt Stage zdarzenia.

## 8. Pakiet: *library.proj.gui.scenes*
### 8.1 SceneHandler
Implementuje interfejs ApplicationListener, który pozwala na obsługę zdarzeń w aplikacji.
- **Adnotacje**:
  - *@Component*: Oznacza klasę jako komponent zarządzany przez kontener Spring.
  - *@Override*: Nadpisuje metodę z klasy bazowej.
- **Metody**:
  - *@Override public void onApplicationEvent(ChangeSceneEvent event)*: Implementacja metody interfejsu ApplicationListener, obsługuje zdarzenie zmiany sceny.

### 8.2 DialogHandler
Implementuje interfejs ApplicationListener, który pozwala na obsługę zdarzeń w aplikacji.
- **Adnotacje**:
  - *@Component*: Oznacza klasę jako komponent zarządzany przez kontener Spring.
  - *@Override*: Nadpisuje metodę z klasy bazowej.
- **Metody**:
  - *@Override public void onApplicationEvent(OpenDialogEvent event)*: Implementacja metody interfejsu ApplicationListener, obsługuje zdarzenie otwarcia okna dialogowego.

### 8.3 SceneCreatorIf (interfejs)
- **Metody**:
  - *Scene createScene(Stage stage, ConfigurableApplicationContext context)*: Deklaracja metody do tworzenia sceny.

### 8.4 SceneCreator (abstrakcyjna)
Implementuje interfejs SceneCreatorIf, który pozwala na tworzenie sceny.
- **Pola**:
  - private final String sceneName: Przechowuje nazwę sceny.
  - private final String stylesheetSource: Przechowuje ścieżkę do pliku ze stylami.
  - protected final FXMLLoader fxmlLoader: Przechowuje obiekt do ładowania plików FXML.
  - private static final int sceneWidth = 800: Stała przechowująca szerokość sceny.
  - private static final int sceneHeight = 600: Stała przechowująca wysokość sceny.
- **Metody**:
  - *public SceneCreator(String sceneName, String fxmlSource, String stylesheetSource)*: Konstruktor, inicjalizuje pola klasy.
  - *public Scene createScene(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody interfejsu SceneCreatorIf, tworzy scenę i zwraca ją.
  - *abstract void setupController(Stage stage, ConfigurableApplicationContext context)*: Metoda abstrakcyjna do ustawiania kontrolera sceny.

>Wszystkie poniższe klasy dziedziczą po klasie SceneCreator.

### 8.5 AddBookCreator
- **Pola**:
    - *private final Stage primaryStage*: Przechowuje referencję do głównego okna aplikacji.
- **Metody**:
    - *public AddBookCreator(Stage primaryStage)*: Konstruktor, inicjalizuje pola klasy.
    - *void setupController(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, ustawia kontroler sceny.

### 8.6 BookDetailsCreator
- **Adnotacje**:
    - *@Override*: Nadpisuje metodę z klasy bazowej.
- **Pola**:
    - *private Book book*: Przechowuje informacje o książce.
    - *private SceneCreator previousScene*: Przechowuje informacje o poprzedniej scenie.
- **Metody**:
    - *public BookDetailsCreator(Book book, SceneCreator scene)*: Konstruktor, inicjalizuje pola klasy.
    - *public Scene createScene(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, tworzy scenę i aktualizuje pola kontrolera.
    - *void setupController(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, ustawia kontroler sceny.

### 8.7 BookListCreator
- **Adnotacje**:
    - *@Override*: Nadpisuje metodę z klasy bazowej.
- **Metody**:
    - *public BookListCreator()*: Konstruktor, inicjalizuje pola klasy.
    - *public Scene createScene(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, tworzy scenę i aktualizuje pola kontrolera.
    - *void setupController(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, ustawia kontroler sceny.

### 8.8 LoginCreator
- **Metody**:
  - *public LoginCreator()*: Konstruktor, inicjalizuje pola klasy.
  - *void setupController(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, ustawia kontroler sceny.

### 8.9 MyRentalsCreator
- **Adnotacje**:
  - *@Override*: Nadpisuje metodę z klasy bazowej.
- **Metody**:
  - *public MyRentalsCreator()*: Konstruktor, inicjalizuje pola klasy.
  - *@Override public Scene createScene(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, tworzy scenę i aktualizuje pola kontrolera.
  - *@Override void setupController(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, ustawia kontroler sceny.

### 8.10 RegisterCreator
- **Metody**:
  - *public RegisterCreator()*: Konstruktor, inicjalizuje pola klasy.
  - *void setupController(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, ustawia kontroler sceny.

### 8.11 RentBookCreator
- **Adnotacje**:
  - *@Override*: Nadpisuje metodę z klasy bazowej.
- **Pola**:
  - *private final Stage primaryStage*: Przechowuje referencję do głównego okna aplikacji.
  - *private final Book book*: Przechowuje informacje o książce.
- **Metody**:
  - *public RentBookCreator(Stage primaryStage, Book book)*: Konstruktor, inicjalizuje pola klasy.
  - *@Override public Scene createScene(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, tworzy scenę i aktualizuje pola kontrolera.
  - *void setupController(Stage stage, ConfigurableApplicationContext context)*: Implementacja metody abstrakcyjnej z klasy bazowej, ustawia kontroler sceny.

## 9. Pakiet: *library.proj.gui.scenes.objects*
### 9.1 BookEntry
Klasa BookEntry dziedziczy po klasie VBox z JavaFX
- **Pola**:
    - *private final Image image*: Obiekt klasy Image reprezentujący obraz okładki książki.
    - *private final ImageView imageView*: Obiekt klasy ImageView służący do wyświetlania obrazu książki.
    - *private final Label title*: Obiekt klasy Label reprezentujący tytuł książki.
    - *private final Label author*: Obiekt klasy Label reprezentujący autora książki.
    - *private static final double entrySize = 120.0*: Stała określająca preferowaną szerokość i wysokość wpisu książki.
    - *private static final double iconSize = 40.0*: Stała określająca preferowany rozmiar ikony okładki książki.
- **Konstruktor**:
    - *public BookEntry(Book book)*: Konstruktor, inicjalizuje pola klasy.

## 10. Graf
>Poniżej przedstawiono graf zależności między klasami w projekcie wygenerowany przez IntelliJ IDEA.
![Graf zależności między klasami](Model%20obiektowy%20graf%20-%20jasny.png)