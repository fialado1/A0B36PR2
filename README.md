#---THE UNEXPECTED ADVENTURE---
========
A.K.A. semestrální práce pro předmět **"Programování 2 - A0B36PR2"**, který je vyučován na [FEL ČVUT](http://www.fel.cvut.cz).

##Obecné info

* Cílem tohoto projektu bude zejména přidání grafické podoby programu - hře, který jsem byl nucen vytvořit jako semestrální práci
pro předmět A0B36PR1 z předcházejícího semestru.
* Jak už název napovídá, jedná o adventuru, avšak okořenil jsem ji trochou RPG prvky, aby byla (pro mě) zábavnější.
* Cílem tohoto projektu je vytvoření programu - hry, který vyhový [podmínkám](https://eduweb.fel.cvut.cz/courses/A0B36PR2/classification/semestralka), které byly stanoveny garantem výše uvedeného předmětu.
* Tato hra bude adventurou obohacou o RPG prvky, aby byla (pro mě) zábavnější. 

##Náznak způsobu řešení
Programovat budu pomocí jazyka JAVA a snahou bude, aby to bylo zcela a správně objektově. Celý projek bude obsahovat relativně hodně tříd, přičemž budou spadat do dvou (zatim) kategorií:
* **grafické uživatelské rozhraní GUI** - třídy sloužící jako prostředník mezi hráčem a "mozkem" programu
* **"mozek"** - třídy spadající do této kategorie budou zpracovávat veškeré pokyny, které hráč učiní a informace, které budou důsledkem oněch pokynů
* **"filtr"** - třída, která bude zaznamenávat stav hry a na základě toho bude umožňovat právě jen ty uživatelské záležitosti, které vyhovují tomu stavu

###GUI
Veškeré vizuální záležitosti budu řešit tím způsobem, že je jednoduš nechám "vytisknout" do okna aplikace.
