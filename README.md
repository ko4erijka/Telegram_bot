# Телеграм бот - погода 
<p align="center"><img src="https://cdn-icons-png.flaticon.com/512/2204/2204335.png" 
 alt="sun"  height="100"  />
  
  
## Описание
Данный бот был написан для месснджера телеграм. И с помощью данного бота, вы можете узнать погоду в своем регионе.
Используется <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1280px-Spring_Framework_Logo_2018.svg.png" 
 alt="Spring"  height="25"/>
## Разработка
  * Язык разработки: **Java 11**
  * Фреймоворк: 
    + <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1280px-Spring_Framework_Logo_2018.svg.png" 
 alt="Spring"  height="10"/> **Boot 2.7.0**
    + <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1280px-Spring_Framework_Logo_2018.svg.png" 
 alt="Spring"  height="10"/> **Cloud Eureka**
    + <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1280px-Spring_Framework_Logo_2018.svg.png" 
 alt="Spring"  height="10"/> **Cloud OpenFeign**
    + <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1280px-Spring_Framework_Logo_2018.svg.png" 
 alt="Spring"  height="10"/> **MVC** 
    + <img src="https://i0.wp.com/proselyte.net/wp-content/uploads/2016/01/Hibernate_logo_a.png" 
 alt="Hibernate"  height="13"/> **Hibernate**
  * Библиотеки: **Lombok, Telegram Boot starter**
  * Внешнее API: [Yandex Weather API](https://yandex.ru/dev/weather/doc/dg/concepts/about.html), [Telegram API](https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter)
  ## Как запустить
  * Eureka Server - приложение
1. Написать команду в консоли, находясь внутри _Eureka Server_ проекта :
```console
    ./gradlew bootrun
```
 * User-Db - приложение
2. Написать команду в консоли, находясь внутри _User-Db_ проекта :
```console
    ./gradlew bootrun
```
   * Weather - приложение
3. Написать команду в консоли, находясь внутри _Weather_ проекта :
```console
    ./gradlew bootrun
```
## Запуск телеграм бота на локальном компьютере.
4. Скачать [ngrok](https://ngrok.com/download)
5. Запустить в консоли ngrok с коммандой:
  
  * Windows
  ```console
    ngrok http 5000
```
  * Mac OS/Linux
  ```console
    ./ngrok http 5000
```
  
6. Установить webhook в браузере, где url - это ваш url ngrok. 
   Пример:
  ```console
      https://76ff-46-188-123-77.ngrok.io
```
7. А mytoken - токен вашего бота. Пример :
 ```console
    https://api.telegram.org/bot(mytoken)/setWebhook?url=https://76ff-46-188-123-77.ngrok.io/mymethod
 ```
