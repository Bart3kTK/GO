# GRA W GO!

## TASKI:
1. DIAGRAMY UML
2. Trzeba dokladniej uzgodnnic
3.
4.
5.
6. .


## Dobra Maciek tu mamy instrukcje
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
5. I gdy zakonczysz juz sesje aktualizacji to robisz merge
    ```bash
    git checkout main #przełączasz sie na main
    git pull origin main #robisz update main
    git merge [nazwa] #i walisz merge
    ```
    Jak beda jakies konflikty to rozwiaz je jak trzeba
6. No i wisienka na torcie czyli
    ```bash
    git push origin main
    ```








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


