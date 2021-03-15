package com.demka.demkaserver.controllers;

import com.demka.demkaserver.models.User;
import com.demka.demkaserver.mongo.MongoDBPOperations;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;


        // For Annotation
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        MongoDBPOperations ops = new MongoDBPOperations();

        Student student = new Student("John", 15);

        //save student
        ops.saveStudent(mongoOperation, student);

        // get student based on search criteria
        ops.searchStudent(mongoOperation, "studentName", "John");

        //update student based on criteria
        ops.updateStudent(mongoOperation, "StudentName", "John", "studentAge", "18");
        // get student based on search criteria
        ops.searchStudent(mongoOperation, "studentName", "John");

        // get all the students
        ops.getAllStudent(mongoOperation);
        //remove student based on criteria
        //ops.removeStudent(mongoOperation, "studentName", "John");
        // get all the students
        //ops.getAllStudent(mongoOperation);


    }

    /** TODO: Авторизация пользователя
     * @return
     */
    @GetMapping(value = "/auth")
    public ResponseEntity<List<User>> auth() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Регистрация пользователя
     * @return
     */
    @GetMapping(value = "/register")
    public ResponseEntity<List<User>> register() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Поиск пользователя в системе (для последующего чата)
     * @return
     */
    @GetMapping(value = "/search")
    public ResponseEntity<List<User>> search() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Отправка сообщения в опеределенную комнату room_id
     * @return
     */
    @GetMapping(value = "/writeMessage")
    public ResponseEntity<List<User>> writeMessage() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * TODO: Получение данных для лонгпула по определенному user_id и его user_key
     * @return
     */
    @GetMapping(value = "/getLongPollServer")
    public ResponseEntity<List<User>> getLongPollServer() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Получение данных для лонгпула по определенному user_id и его user_key
     * @return
     */
    @GetMapping(value = "/LongPoll/<server>")
    public ResponseEntity<List<User>> longpoll() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    /**
     * TODO: Создание комнаты для общения пользователей
     * @return
     */
    @GetMapping(value = "/createRoom")
    public ResponseEntity<List<User>> createRoom() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Получение инфорации о комнате
     * @return
     */
    @GetMapping(value = "/getRoomInfo")
    public ResponseEntity<List<User>> getRoomInfo() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * TODO: Получение истории сообщений определенной комнаты
     * @return
     */
    @GetMapping(value = "/getRoomMessagesHistory")
    public ResponseEntity<List<User>> getRoomMessagesHistory() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
