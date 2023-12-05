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
# M1
## 1. Wprowadzanie nowego użytkownika (imię, nazwisko, email) oraz sprawdzenie poprawności danych
Wprowadzanie nowego użytkownika odbywa się za pomocą funkcji *invoke()* klasy *PersonAdder* z pakietu *library.proj.CLI*.\
Funkcja ta zczytuje z konsoli dane nowego użytkownika, sprawdza czy hasło jest wystarczająco długie (min. 8 znaków) oraz czy podany email jest poprawny (za pomocą regexu).\
W przypadku niepoprawnych danych, użytkownik jest proszony o ponowne wprowadzenie danych.\
Jeśli dane są poprawne, nowy użytkownik zostaje dodany do bazy za pomocą funkcji *savePerson()* klasy *PersonService* z pakietu *library.proj.service*.

## 2. Model bazodanowy
![Model bazodanowy](schemat%20bazy.png)

## 3. Model obiektowy
