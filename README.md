## Table of contents
* [Technologies](#technologies)
* [General info](#general-info)
* [Application description](#Application-description)
* [Hibernate](#Hibernate)
	

## Technologies
<img src="https://hsto.org/webt/rg/a1/3b/rga13bp-mbl4ljkpbd-fuu6pzfw.png" alt="drawing" height=70px/><img src="https://vignette.wikia.nocookie.net/jfx/images/5/5a/JavaFXIsland600x300.png/revision/latest?cb=20070917150551" alt="drawing" height=70px/><img 
src="https://i0.wp.com/gluonhq.com/wp-content/uploads/2015/02/SceneBuilderLogo.png?fit=781%2C781&ssl=1" alt="drawing" height=70px/><img 
src="https://www.techcentral.ie/wp-content/uploads/2019/07/Java_jdk_logo_web-372x210.jpg" alt="drawing" height=70px/><img 
src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/IntelliJ_IDEA_Logo.svg/1024px-IntelliJ_IDEA_Logo.svg.png" alt="drawing" height=70px/> 
<img src="https://kosiorowski.net/wp-content/uploads/2013/11/hibernate1.png" alt="drawing" height=70px/> 
<img src="https://avatars1.githubusercontent.com/u/874086?s=200&v=4" alt="drawing" height=70px/>
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Maven_logo.svg/1024px-Maven_logo.svg.png" alt="drawing" height=50px/>


## General info
Projekt studencki na zajęcia z Technologii programowania
Tworzony przez:
[Michał Kalina](https://github.com/KalinaMichal) oraz 
[Maciej Lewandowicz](https://github.com/sasuke5055) , 
[Tresc zadania](https://cs.pwr.edu.pl/macyna/TPLab04.pdf)

## Application description
Aplikacja pozwalająca grać w grę *GO* z możliwością gry z BOTem lub drugą osobą.
Logika gry znajduje się po stronie servera.
Każdy ruch zapisywany jest w bazie danych przy pomocy Hibernate.

<img src="https://github.com/KalinaMichal/projectGO/blob/master/Tresc_zadania/pics/menu.png" alt="drawing" height=350px/> 

Po wybraniu trybu aplikacja próbuje połączyć się z serwerem i rozpocząć grę. Przy każdej próbie ruchu użytkownik wysyła do serwera informację. 

<img src="https://github.com/KalinaMichal/projectGO/blob/master/Tresc_zadania/pics/loading.png" alt="drawing" height=350px/> 
<img src="https://github.com/KalinaMichal/projectGO/blob/master/Tresc_zadania/pics/game.png" alt="drawing" height=350px/> 
<img src="https://github.com/KalinaMichal/projectGO/blob/master/Tresc_zadania/pics/gif.gif" alt="drawing" height=350px/> 

W przypadku gdy obaj gracze w swojej turze wybiorą opcję *Pass*, zostanie uruchomiony chat. W którym gracze decydują kto wygrał. W przypadku gdy gracze nie dojdą do porozumienia, można wznowić grę.
          
<img src="https://github.com/KalinaMichal/projectGO/blob/master/Tresc_zadania/pics/chat1.png" alt="drawing" height=350px/> 
<img src="https://github.com/KalinaMichal/projectGO/blob/master/Tresc_zadania/pics/chat2.png" alt="drawing" height=350px/> 

## Hibernate
W projekcie został zastosowany framework Hibernate. 
Po każdym ruchu, dane (id gry, współrzędne, kolor) są zapisywane w bazie danych ``checkersdb``. 
Na potrzeby programu został stworzony user: ``hiber`` o haśle: ``123Hiber#``.
