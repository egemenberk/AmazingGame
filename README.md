

## Term Project of Ceng453 Software Construction

This repository will include the files of an interactive multiplayer video game as a term project. The game that is a simple two-dimensional shooter game. Player controls an auto-shooting spaceship by moving it freely (with mouse) in two dimensions to shoot aliens. There will be at least two types of aliens with different attacking capabilities and at least one type of aliens will require to be hit at least two times to be destroyed.

### Game Server (/Frontend/src/main/com/ceng453/server/Main.java)

Game Server provides functionalities that is required for multiplayer gaming experience.

##### Challenges, Engineering Details and Our Solutions can be found in :

https://docs.google.com/document/d/1tYyyYIOC2_8sbwv5sWxir12NWoXRUdOZrRJEqWeQ-dg/edit?usp=sharing

#### Executing the provided .jar file

* Game Server can be opened before or after REST Server, they do not depend on each other.
* Game Server .jar file can be found at executables/Server.jar
* You need to open this jar file using command `java -jar {path to downloaded server jar}`

### Game Client (/Frontend/src/main/com/ceng453/client/main/Main.java)

Game client, written in Java 11 with FX, serves the end user with the whole semi-multiplayer game functionalities. 

#### Executing the provided .jar file

* Before the Client, both REST and Game Server should have been opened. 
* Game Client .jar file can be found at executables/amazingGame.jar
* You need to open this jar file using : `java --module-path {JavaFX 11 lib folder path} --add-modules=javafx.controls,javafx.fxml,javafx.media -jar {path to downloaded .jar file} `
  * We have `java 11.0.1 2018-10-16 LTS`, `Java(TM) SE Runtime Environment 18.9 (build 11.0.1+13-LTS)` and `JavaFX SDK 11.0.1` in our environment.

#### Test Cases Document

https://docs.google.com/document/d/1lbez5QFCndMBGf3pxmxmC3l9Er7d0J8kT7CP-xUXoBA/edit

### REST Server (/Server/src/main/java/com/ceng453/Server/ServerApplication.java)

#### Executing the provided .war file

* REST Server does not depend on Game Server, order of execution is not important.
* REST Server .war file can be found at executables/Server.war
* Use Tomcat deploy interface to deploy the war file to your system. Endpoints should be reachable from <BASE_ADDRES>/Server/ path. 

#### Functionality Document & Example Usage

https://docs.google.com/document/d/1OROqx9ZCg_Jc37M5iAIwq-vv1r0wfyAZO2GgxwUMIB8/edit?usp=sharing

##### DB design
- Can be found on DBDesign.pdf
- Create table statements can be found on create_table_statements.sql

##### Postman collection is named as Ceng453.postman_collection.json !

##### Meeting agenda of our group(OUT OF DATE):

https://docs.google.com/document/d/1Yzq4HwFckbgsnHESoSFBUl5fOv8kAMfpTr2_BZCD3cg/edit?usp=sharing



