# Gestione corsi

Il progetto permette di gestire informazioni riguardo un corso di laurea, per l'admin della piattaforma sarà possibile aggiungere o rimuovere corsi, per gli user (docenti) gestire informazioni sui loro corsi.

## Getting started

Modificare il file `application.properties` in `src/main/resources` aggiungendo al posto di `?` le informazioni per l'accesso al database, url, username e password 

```shell
spring.datasource.url= your-url-to-db (es jdbc:mysql://localhost:3306/NomeDB)
spring.datasource.username= YourUsername
spring.datasource.password= YourPassword
```
Utilizzare Maven per la build
```shell
mvn clean install
```
Il jar ottenuto può esere quindi lanciato 