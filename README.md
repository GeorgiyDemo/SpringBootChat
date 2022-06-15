# SpringBootChat
Chat Long Polling Implementation with Java Spring Boot

## Backend

### Stack
- Spring Web
- Spring Data MongoDB
- Lombok
- Maven
- Docker

### Initialization
You need to create .env file in a root folder of this project with the following template:

```shell
MONGO_LOGIN=YOUR_MONGO_LOGIN
MONGO_PASSWD=YOUR_MONGO_PASSOWRD
MONGO_DB=YOUR_MONGO_DATABASE
MONGO_URL=mongodb://YOUR_MONGO_LOGIN:YOUR_MONGO_PASSOWRD@IP:PORT/?authSource=admin&authMechanism=SCRAM-SHA-1
```

After that you can run docker-compose.yml file to start the application:
```shell
docker compose up -d
```

## Frontend

Stack:
- JavaFX (javafx-controls, javafx-fxml, javafx-graphics)
- JFoenix
- SLF4J
- GSON
- Maven

### Configure with your personal backend
To change server URL you need to write your personal logic in AbstractAPI.getServerUrl() method.
For example:
```java
static String getServerUrl() {
  return "http://127.0.0.1:8080";
}
```

### UI

#### Main form
<img src="https://github.com/GeorgiyDemo/SpringBootChat/blob/img/main.png"  width="980" height="429"/>


#### New сhat form
<p align="center">
    <img src="https://github.com/GeorgiyDemo/SpringBootChat/blob/img/create.png"  width="739" height="467"/>
</p>

#### Login form
<p align="center">
  <img src="https://github.com/GeorgiyDemo/SpringBootChat/blob/img/login.png"  width="501" height="497" 
</p>


More info can be found [here](https://github.com/GeorgiyDemo/FA/blob/master/more/courseworks/SpringBootChat/%D0%9F%D0%9819-4%20%D0%94%D0%B5%D0%BC%D0%B5%D0%BD%D1%87%D1%83%D0%BA%20%D0%93%D0%B5%D0%BE%D1%80%D0%B3%D0%B8%D0%B9%20%D0%9A%D1%83%D1%80%D1%81%D0%BE%D0%B2%D0%B0%D1%8F.pptx) (Russian lang, .pptx file)