## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [JavaSetup](#java-Setup)
* [New Maven project](#tworzenie-nowego-projektu-Maven-w-programie-InteliiJ)
	
	
## Java Setup
 Usuwanie starej i instalowanie najnowszej wersji Java w systemie LINUX Ubuntu

```
$ sudo update-alternatives --remove "java"
$ cd /usr/lib/jvm
$ sudo rm -r java-1.8.0-open-amd64
$ sudo rm -r java....
$ sudo rm -r jdk-13.0.1
$ sudo rm -r jvm
$ cd $HOME
$ nano .bashrc file
```
zmiana pliku
Dodaj: 
```
export PATH=/home/michal/Pobrane/openjdk-13.0.1_linux-x64_bin/jdk-13.0.1/bin:$P$
source .bashrc
```
Jest to sciezka do plików

## General info
Projekt studencki na zajęcia z Technologii programowania
Tworzony przez:
[Michał Kalina](https://github.com/KalinaMichal) oraz 
[Maciej Lewandowicz](https://github.com/sasuke5055) , 
[Tresc zadania](https://cs.pwr.edu.pl/macyna/TPLab04.pdf)

## Technologies
<img src="https://hsto.org/webt/rg/a1/3b/rga13bp-mbl4ljkpbd-fuu6pzfw.png" alt="drawing" height=50px/><img src="https://vignette.wikia.nocookie.net/jfx/images/5/5a/JavaFXIsland600x300.png/revision/latest?cb=20070917150551" alt="drawing" height=50px/><img 
src="https://i0.wp.com/gluonhq.com/wp-content/uploads/2015/02/SceneBuilderLogo.png?fit=781%2C781&ssl=1" alt="drawing" height=50px/><img 
src="https://www.techcentral.ie/wp-content/uploads/2019/07/Java_jdk_logo_web-372x210.jpg" alt="drawing" height=50px/><img 
src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/IntelliJ_IDEA_Logo.svg/1024px-IntelliJ_IDEA_Logo.svg.png" alt="drawing" height=50px/> <img src="https://kosiorowski.net/wp-content/uploads/2013/11/hibernate1.png" alt="drawing" height=50px/> 




## Tworzenie nowego projektu Maven w programie InteliiJ

* Tworzymy nowy projekt ConsoleAplication JavaFX
* Następnie prawym przyciskiem wybieramy nazwe projektu i Add Freamworks Support
* Wybieramy Maven-a
* Ustawiamy FIle->Setting->Build,Execution,D...->JavaCompiler
* Project bytecode version: 12
* Target bycode version: 12
* zmienimy plik pom.xml na:
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.mycompany.app</groupId>
    <artifactId>projectGO</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>11</maven.compiler.release>
    </properties>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>projectGO</artifactId>
                    <version>3.8.1</version>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
```

Słuzy to temu abysmy mogli kozystac z nowszej bytecode version. Gdybysmy zostawili domyslny kod to musielibyśmy zmienić Target bycode version na 6 

