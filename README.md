# mybatis-spring-boot-jpetstore

[![Java CI](https://github.com/kazuki43zoo/mybatis-spring-boot-jpetstore/actions/workflows/ci.yaml/badge.svg)](https://github.com/kazuki43zoo/mybatis-spring-boot-jpetstore/actions/workflows/ci.yaml)
[![Dependency Check](https://github.com/kazuki43zoo/mybatis-spring-boot-jpetstore/actions/workflows/dependency-check.yaml/badge.svg)](https://github.com/kazuki43zoo/mybatis-spring-boot-jpetstore/actions/workflows/dependency-check.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=kazuki43zoo_mybatis-spring-boot-jpetstore&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=kazuki43zoo_mybatis-spring-boot-jpetstore)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=kazuki43zoo_mybatis-spring-boot-jpetstore&metric=coverage)](https://sonarcloud.io/summary/new_code?id=kazuki43zoo_mybatis-spring-boot-jpetstore)

This sample is a web application built on MyBatis, Spring Boot(Spring MVC, Spring Security) and Thymeleaf.
This is another implementation of MyBatis JPetStore sample application (https://github.com/mybatis/jpetstore-6).

Original application is available for downloading in the downloads section of MyBatis project site.
In this section, we will walk through this sample to understand how is it built and learn how to run it.

> **Note**
>
> This sample application is under development.
> If you found an issue, please report from [here](https://github.com/kazuki43zoo/mybatis-spring-boot-jpetstore/issues/new).

## Requirements

* Java 8+ (JDK 1.8+)

## Stacks

* MyBatis Spring Boot Starter 2.2 (MyBatis 3.5, MyBatis Spring 2.0)
* Spring Boot 2.7 (Spring Framework 5.3, Spring Security 5.7)
* Thymeleaf 3.0
* Hibernate Validator 6.2 (Bean Validation 2.0)
* HSQLDB 2.5 (Embed Database)
* Flyway 8.5 (DB Migration)
* Tomcat 9.0 (Embed Application Server)
* Groovy 4.0 (Use multiple line string on MyBatis Mapper method)
* Lombok 1.18
* Selenide 6.5
* Selenium 4.1
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

## Run using java command

* Build a jar file

  ```
  $ ./mvnw clean package -DskipTests=true
  ```

* Run java command

  ```
  $ java -jar target/mybatis-spring-boot-jpetstore-2.0.0-SNAPSHOT.jar
  ```

## Perform integration test using Maven command

Perform integration tests for screen transition.

```
$ ./mvnw clean test
```


## Run on IDEs (Note)

This sample use the [Lombok](https://projectlombok.org/) to generate setter method, getter method and constructor.
If this sample application run on your IDE, please install the Lombok. (see [https://projectlombok.org/download.html](https://projectlombok.org/download.html))

And this application use the groovy language to use multiple line string on MyBatis Mapper method.
If this sample application run on your IDE, please convert to groovy project and add `src/main/groovy` into source path.
And if you use a STS(or Eclipse), please install the Groovy Eclipse plugin. About how install the Groovy Eclipse, please see as follow:

* https://github.com/groovy/groovy-eclipse/wiki


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

## Access to the index page

[http://localhost:8080/](http://localhost:8080/)

![Index Screen](images/screen-index.png)

![Catalog Screen](images/screen-catalog.png)


## Default active accounts (ID/PASSWORD)

* j2ee/j2ee
* ACID/ACID

## Data Store

In this application, application data stored in filesystem files.

```
$HOME
  └── db
      + jpetstore.script
      + jpetstore.properties
```

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
        │               │   ├── event
        │               │   ├── exception
        │               │   ├── message
        │               │   └── validation
        │               ├── config         // Store configuration classes
        │               ├── domain         // Store domain objects
        │               ├── service        // Store service classes
        │               └── ui             // Store classes that depends user interface
        │                   └── controller // Store controller classes
        └── resources
            ├── db                         // Store sql files for Flyway
            │   └── migration
            ├── static                     // Store static web resource files
            │   ├── css
            │   └── images
            └── templates                  // Store view template files for Thymeleaf
                ├── account
                ├── auth
                ├── cart
                ├── catalog
                ├── error
                └── order
```
