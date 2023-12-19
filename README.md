# GRA W GO!

## TASKI DO 20.12:
1. Diagramy UML
2. Proste działanie klient serwer
3. Proste GUI i planszą, która pozwala klaść pionki
4.
5.
6. ...

## TASKI DO KONCA SEMESTRU:
1. Diagram klas
2. Logika
3.
4.
5. ...

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
    - *BoardGUI* - kalsa posiadajaca kwadraty z zamalowanym marginesem oraz posiadajaca obiekty kalsy **PawnGUI** na wszystkich mozliwych polach w stanie invisible. Okno musi posiadać przycisk pass i surrender   . Klasa również przchwytuje kliknięcia użytkownika **Server connection**

## Część serwerowa
1. 

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


