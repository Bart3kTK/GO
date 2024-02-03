# GRA W GO!
# **Uruchomienie**
Program korzysta z bazy MarieDB i łączy się za pomocą JDBC
Baza, z którą się łączy musi mieć nazwę go_games oraz
posiadać dwie tabele i użytkownika o nazwie server
i haśle GoGoPowerRanger

```bash
CREATE TABLE game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_column DATETIME,
    index_gry INT,
    INDEX idx_date (date_column),
    INDEX idx_index_gry (index_gry)
);


CREATE TABLE board (
    id INT AUTO_INCREMENT PRIMARY KEY,
    index_gry INT,
    board VARCHAR(3000),
    INDEX idx_index_gry (index_gry),
    INDEX idx_board (board)
);

CREATE USER 'server'@'localhost' IDENTIFIED BY 'GoGoPowerRangers';
GRANT ALL PRIVILEGES ON *.* TO 'server'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

```

## wlaczenie klenta
- w ścieżce Go/gogo mvn javafx:run
## wlaczenie servera
- w ścieżce Go/server mvn clean install oraz mvn exec:java

# **Założenia**
## Część klienta
1. PionkiGUI musza dziedziczyc po kole z javafx
2. Board z tablciami idzie na serwer
3. Trzeba utworzyc nowy board (BoardGUI)
4. Utorzyc kalsę StartGUI (proste)
5. Utworzyć klasę do kontaktu z serwerem 
4. Klient posiada klasy:
    - StartGUI
    - ClientConnection
    - PawnGUI
    - BoardGUI
5. Opis klas:

    - *StartGUI* - toggle group button do wyboru trybu gry, wybór rozmiaru planszy. **Server connection**
    - *ClientConnection* - klasa odpowiedzilna za kontak z serwerem. **Server connection**
    - *PawnGUI* - kalsa dziedziczaca po kole, domyślnie będąca niewidoczna, zawiera zmienne x, y, color oraz metody setWhite(colorWhite, visible), setBlack(colorBlack, visible), setInvisible.
    - *BoardGUI* - kalsa posiadajaca kwadraty z zamalowanym marginesem oraz posiadajaca obiekty kalsy **PawnGUI** na wszystkich mozliwych polach w stanie invisible. Okno musi posiadać przycisk pass i surrender. Klasa również przchwytuje kliknięcia użytkownika **Server connection**

## Część serwerowa

1. Serwer posiada klasy:
- Main
- PvpGame
- BotGame
- Bot
- Board
- Logic
- Settings
2. Opis klas:
    - *Main* - tworzy serwer o socket 8888. Plan jest taki, ze klasa ma trzy kolejki typu soket w pętli whlie(true) bedzie przyjmowany nowy socket, nastepnie beda pobierane od niego informacje na temat wybranego trybu gry oraz rozmiaru planszy. W dlaszej czesci while bedzie if'y ktore najpierw albo odpalaja gre z botem albo dodaja gracza do odpowiedzniej kolejki a potem bedzie sprawdzane czy kolajka ma wiecej niz 2 el jesli tak to sprawdza czy dwa kolejne sa aktywne jesli oba git to nowa gra jesli nie to usuwany ten nie git.
    - *PvpGame* - klasa implementujaca runnable, ktora przyjmuje dwa sockety od aktwynych graczy, posiada obiek klasy **Logic** 
    - *Bot Game* - klasa implementujaca runnable, ktora przyjmuje jeden socket od atywnego gracza, posiada obiek klasy **Logic** oraz posiada obiekt kalsy **Bot**, która emituje drugi socket 
    - *Bot* - implementacja bota
    - *Logic* - sprawdzanie lagalnosci ruchu, sprawdzanie czy nastapilo uduszneie itd. cala logika gry
    - *Board* klasa posiadajaca informacje o planszy

## Prboblemy do rozwiazania
1. Settings *problem rozwiazany*

## Uruchamianie projektu gogo (część kliena)
```bash
mvn javafx:run
```
## Uruchamianie projektu server (część serwera)
```bash
mvn clean install #kompilacja
mvn exec:java #opalanie
```
## Dobra Maciek tu mamy instrukcje dotycząca Git'a i GitHub'a 
1. wiec jesli robimy cos przy projekcie to dajemy
    ```bash
    git pull origin main
    ```

2. To wiadomo da nam update wszytstkiego.
Dalej robimy nowego brancha na ktorym bedziemy wprowadzac własne zmiany
    ```bash
    git checkout -b "nazwa"
    ```

3. Albo alternatywnie mozesz utworzyc nowa galaz i do niej przejsc osobno
    ```bash
    git branch [nazwa] #tworzysz gałąź
    git checkout [nazwa] #przełączasz się na nią
    ```
4. Potem wiadomo każde nowe zmiany wprowadzasz tak
    ```bash
    git add .
    git commit -m "Opis zmian"
    ```
5. I gdy zakonczysz juz sesje aktualizacji to nie robisz merge tylko **git push origin**
    ```bash
    git push origin [nazwa] 
    ```
6. Potem wbijasz na GitHuba i wchodzisz w branches, szukasz tego co robiles wlasnie i klikasz trzy kropki i klikasz pull request no i tam dodajesz jakis kom i potem mozna to bezpiecznie zmergowac









# **ŚCIĄGA DO PLIKU .MD**

```python

    # Nagłówek 1
    ## Nagłówek 2
    ### Nagłówek 3

    **Pogrubienie**
    *Kursywa*

    Lista nieuporządkowana:

    - Element 1
    - Element 2
    - Pod-element A
    - Pod-element B


    Lista upo
    1. Punkt pierwszy
    2. Punkt drugi


    Linki:

    markdown

    [Tekst linku](adres_url)

    Obrazy:


    ![Tekst alternatywny](adres_url_obrazu)

    Cytaty:

    

    > To jest przykład cytatu.

    Kod źródłowy:

        Kod w linii:

        

    To jest `kod` wewnątrz zdania.

    Blok kodu:

    

    ```python
    def funkcja():
        print("Hello, World!")


    Tabele:

    

    | Nagłówek 1 | Nagłówek 2 |
    |------------|------------|
    | Komórka 1  | Komórka 2  |
    | Komórka 3  | Komórka 4  |

    Horyzontalne linie:

    

    ---

    Escape:

    \*To nie jest pogrubienie\*


