@echo off
set CLASS_PATH=./myBin
javac -Xlint -d %CLASS_PATH% -cp ./src ./src/gestioneScuola/*.java
javac -Xlint -d %CLASS_PATH% -cp ./src ./src/gestioneScuola/menu/*.java
javac -Xlint -d %CLASS_PATH% -cp ./src ./src/librerie/gestioneConsole/*.java
javac -Xlint -d %CLASS_PATH% -cp ./src ./src/librerie/gestioneDb/*.java
java -cp ./src/librerie/gestioneDb/mysql-connector-java-8.0.12.jar;./myBin gestioneScuola.Main