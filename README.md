# mybatis-spring-boot-jpetstore

This sample is a web application built on MyBatis, Spring Boot(Spring MVC, Spring Security) and Thymeleaf.
This is another implementation of MyBatis JPetStore sample application (https://github.com/mybatis/jpetstore-6).

Original application is available for downloading in the downloads section of MyBatis project site.
In this section, we will walk through this sample to understand how is it built and learn how to run it.

> **Note:**
>
> This sample application is under development.
> If you found an issue, please report from [here](https://github.com/kazuki43zoo/mybatis-spring-boot-jpetstore/issues/new).


## Stacks

* MyBatis Spring Boot Starter 1.1 (MyBatis 3.4, MyBatis Spring 1.3)
* Spring Boot 1.4 (Spring Framework 4.3, Spring Security 4.1)
* Thymeleaf 3.0
* HSQLDB 2.3 (Embed Database)
* Tomcat 8.5 (Embed Application Server)
* Java 8 
* Groovy 2.4 (Use multiple line string on MyBatis Mapper method)
* Lombok 1.16
* etc ...

## Run using Maven command

* Clone this repository

  ```
  $ git clone https://github.com/kazuki43zoo/mybatis-spring-boot-jpetstore.git
  ```
  
* Run a web application using the spring-boot-plugin

  ```
  $ cd mybatis-spring-boot-jpetstore.git
  $ ./mvnw clean spring-boot:run
  ```

* Access a top page ([http://localhost:8080/](http://localhost:8080/))

## Run using java command

* Build a jar file

  ```
  $ ./mvnw clean package
  ```

* Run java command

  ```
  $ java -jar target/mybatis-spring-boot-jpetstore-1.0.0-SNAPSHOT.jar
  ```

## Run on IDEs (Note)

This sample use the [Lombok](https://projectlombok.org/) to generate setter method, getter method and constructor.
If this sample application run on your IDE, please install the Lombok. (see [https://projectlombok.org/download.html](https://projectlombok.org/download.html))

And this application use the groovy language to use multiple line string on MyBatis Mapper method.
If this sample application run on your IDE, please convert to groovy project and add `src/main/groovy` into source path.

e.g.) multiple line string on MyBatis Mapper method

```groovy
@Mapper
@CacheNamespace
interface CategoryMapper {

    @Select('''
        SELECT
            CATID AS categoryId,
            NAME,
            DESCN AS description
        FROM
            CATEGORY
        WHERE
            CATID = #{categoryId}
    ''')
    Category getCategory(String categoryId)

}
```


## Default active accounts (ID/PASSWORD)

* j2ee/j2ee
* ACID/ACID


## Project Structure

Project structure of this sample application is as follow:

```
.
└── src
    └── main
        ├── groovy
        │   └── com
        │       └── kazuki43zoo
        │           └── jpetstore
        │               └── mapper         // Store mapper interfaces
        ├── java
        │   └── com
        │       └── kazuki43zoo
        │           └── jpetstore
        │               ├── component      // Store general component classes
        │               ├── config         // Store configuration classes
        │               ├── domain         // Store domain objects
        │               ├── service        // Store service classes
        │               └── web            // Store classes that depends on web layer
        │                   └── controller // Store controller classes
        └── resources
            ├── static                     // Store static web resource files
            │   ├── css
            │   └── images
            └── templates                  // Store view template files
                ├── account
                ├── auth
                ├── cart
                ├── catalog
                ├── error
                └── order
```
