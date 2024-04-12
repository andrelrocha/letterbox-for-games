## 💻 About

GeekCatalog API is designed to manage media and art content not covered by other widely-used market applications like Letterboxd. It offers users the capability to manage various types of media such as series, anime, games, or any other implemented media types, through CRUD operations following the business requirements. The API provides endpoints following REST standards, as well as multimedia communication channels, enabling users to manage storage and media responses such as images in the database for frontend consumption.

The API is built using Spring Boot and incorporates Flyway and Hibernate for database management, adhering to best practices in communication with the database. It manages multiple schemas within a multi-application schema structure, accommodating a legacy application already developed by me in: [https://github.com/andrelrocha/backlog-app-API].

## Documentation API

- Run the project by executing ApiApplication
- Check the visual interface of the documentation at http://localhost:8080/swagger-ui/index.html
- Access the JSON documentation at http://localhost:8080/v3/api-docs
- To build and run the project using Maven:
```
mvn package
java -jar target/your-project-name.jar
```
This will first build the project, package it into a JAR file, and then you can execute the JAR using the java -jar command. 
Replace your-project-name.jar with the actual name of your generated JAR file.

---

## ⚙️ Functionalities

- [x] Handling and conversion of information from .csv and .xlsm tables to entities properly mapped in the system, facilitating the control of this information with application-specific algorithms
- [x] User custom system with different levels of permissions
- [x] Login system with JWT authentication
- [x] CRUD for different sort of media types, categorized as backlog, such as games, animes, mangas, with their er relationships
- [x] Routine for all entities in the system, easily traceable by the user
- [x] Comment system allowing users to add both private and public comments in a social app-like format
- [x] List management system, with users able to rate, indicate medium which they consumed the entity, and provide personal notes for them
- [x] Image storage with file compression system, facilitating quick communication with the front-end
- [x] CRUD for games, with image storage in the database and return in an ideal format for display on the front-end
- [x] Token validation system to be used by the front-end
- [x] Custom search system with pagination, enabling complete and customized access by the front-end
- [x] All endpoints mapped in the REST standard
- [x] Security schema implemented in the backend for requests in different layers

---

## 🛠 Technologies

The following technologies were used in the development of the REST API project:

- **[Java 17](https://www.oracle.com/java)**
- **[Spring Boot 3](https://spring.io/projects/spring-boot)**
- **[Maven](https://maven.apache.org)**
- **[Postgresql](https://www.postgresql.org/)**
- **[Hibernate](https://hibernate.org)**
- **[Flyway](https://flywaydb.org)**
- **[Lombok](https://projectlombok.org)**
- **[JWT](https://jwt.io/)**
- **[IGDB-API](https://www.igdb.com/api)**
