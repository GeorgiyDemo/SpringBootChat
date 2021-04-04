module org.demka {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens org.demka to javafx.fxml;
    opens org.demka.controllers to javafx.fxml;
    exports org.demka;
    exports org.demka.controllers;
}