#---THE UNEXPECTED ADVENTURE---
========
A.K.A. semestrální práce pro předmět **"Programování 2 - A0B36PR2"**, který je vyučován na [FEL ČVUT](http://www.fel.cvut.cz).

##Zadání
Naprogramujte hru - adventuru, přičemž  součástí bude engine, která se bude odehrávat v budově o několika přístupných místnostech. K dispozici budou různé předměty ukryté v místnostech, které budou muset vhodně posbírat a zkombinovat pro splnění cíle (útěku). Navíc bude možnost hru uložit a poté ji nahrát.
##Pravidla hry
Cílem hry, respektive první kapitoly, je dostat se na schodiště, odkud vede cesta do dalšího patra – druhá kapitola. K tomu je zapotřebí nejprve splnit tyto podúkoly: 
1) odblokování dveří vedoucí z toalety na chodbu díky použití předmětů, které jsou k nalezení, ve správném sledu
2) zkombinovat předmět tak, aby vznikla funkční mpTrojka a použít ji
3) zkombinovat předmět tak, aby vznikl šperhák a použít jej k odblokování dveří vedoucích na schodiště.

##Vlastnosti enginu
Momentálně je engine v takové fázi vypracování, že umožňuje:
načtení obrázku použitých pro tvorbu vzhledu a ostatních potřebných dat, a tím reprezentaci „světa“
práci s hudbou hrající v pozadí
načítání a ukládání hry
##Ovládání
Program se primárně ovládá myší, kdy hráč pomocí kliknutí na vhodný komponent vyvolá danou akci, avšak některé je možné vyvolat pomocí klávesnice (I - otevření/zavření inventáře a informační tabulky, atd...). Klávesnice je ještě třeba při zadávání textu do textových polích (jméno hráčovy postavy, název ukládané hry).

##Způsob řešení
Programovat budu pomocí jazyka JAVA a snahou bude, aby to bylo zcela a správně objektově.
###Architektura
Program a tedy jednotlivé třídy, které jsou v něm implementovány, je vnitřně členěn do tří hlavních částí:
* **Model** – reprezentuje stav hry.
* **View** – převádí data reprezentovaná modelem do podoby vhodné k interaktivní prezentaci hráči.
* **Controller** – zajišťuje komunikaci mezi modelem a view a tím celou interakci hry.

###Implementace
Program se skládá ze tří balíčku, které odpovídají architektonickému členění, a třídy pro primární spuštění hry. Níže je uveden stručný popis některých tříd (těch zajímavějších).

####Třída CreateAMap
Pomoci této třídy a vytvořením její instance v třídě Game, vytvářím vlastni „svět“, ve
kterém se hráč může pohybovat. Nejprve se vytvoří místnosti pomoci konstruktoru třídy
Room (viz. níže), které se pak vhodně pospojuji do čtyřnásobného spojového seznamu pomoci
metody setSmer() (čtyři světové strany → čtyřnásobný spojový seznam). 
Parametr typu String v konstruktoru určuje z jaké složky se mají načíst potřebná data, díky čemuž lze pohodlně určit, jestli se má vytvořit nový svět (Nová hra) nebo načíst již ten rozehraný (Načíst hru).
####Třída Room
V konstruktoru teto třídy dochází pro příslušnou místnost (identifikace podle nazvu místnosti) k
načteni dat z textového souboru. Následně se tyto hodnoty přiřadí k příslušným parametrům, které
lze pak získávat pomoci „getterů“ . Každé místnosti je přiřazen array list, do kterého se ukládají
předměty, jenž se tam vyskytuj, a to ať ty co jsou tam od prvopočátku, nebo ty které tam hráč vloží. V konstruktoru se opět pomocí Stringu určuje z jaké složky se má načíst textový soubor ve kterém je zaznamenáno jaké předměty jsou v místnosti.
####Třída GUI
Hlavni třída view. Je to okno, jehož kontajner pro komponenty je "obohacen" o JLayeredPane, díky čemuž je možné vkládat potřebné panely do různých vrstev.
