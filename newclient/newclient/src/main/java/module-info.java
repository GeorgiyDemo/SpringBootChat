module org.demka {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.google.gson;
    requires org.slf4j;
    requires java.desktop;
    requires junit;

    opens org.demka to javafx.fxml;
    opens org.demka.controllers to javafx.fxml;

    exports org.demka;
    exports org.demka.controllers;
    exports org.demka.tests to junit;
}