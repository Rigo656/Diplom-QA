# Дипломный проект профессии «Тестировщик ПО»


##Процедура запуска авто-тестов:

---
* На локальном компьютере должны быть установлены: IntelliJ IDEA Ultimate и Docker
* Склонировать репозиторий c [Дипломным проектом](https://github.com/Rigo656/Diplom-QA.git) и открыть его в IntelliJ IDEA Ultimate.
* Запустить Docker;
* В терминале развернуть контейнер с помощью команды

   >docker-compose up

* В новом терминале запустить целевое приложение:

   >для mySQL - java -jar artifacts/aqa-shop.jar --spring.profiles.active=mysql

   >для postgresgl - java -jar artifacts/aqa-shop.jar --spring.profiles.active=postgresql

* В новом терминале запустить тесты используя команды:

   >для mySQL - ./gradlew test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass

   >для postgresgl - ./gradlew test -Durl=jdbc:postgresql://localhost:5432/app -Duser=app -Dpassword=pass

* В новом терминале сформировать отчет командой

   >./gradlew allureServe

