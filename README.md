"# simple-bug-tracker" 
This project contains simple bug tracker system of Spring boot Web application + JSF + Spring Data + PosgreSQL database.
System requirements:
JDK 1.8
PostgreSQL 9.4 database 

To configure project for database connection, edit src\main\resources\application.properties file/
To build this project use:
mvn clean install
Before start application, create database objects:
1.	As database administrator run create_user_database.sql which creates bugtrack database and user bt with password bt 
2.	Connect as user bt to database bugtrack and run script create_tables.sql.
Run the single executable jar file simple-bug-tracker-0.1.0.jar is located in  target directory:
java -jar simple-bug-tracker-0.1.0.jar
Open your favorite browser and connect to http://localhost:8080/
Enter admin as user name and change_it as password as password in the login form.


