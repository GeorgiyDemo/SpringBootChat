package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        Controller controller = loader.getController();
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        System.setProperty( "log4j.configurationFile", "support/xml/log4j2.xml" );
        LocaleLogger logger = new LocaleLogger();
        logger.log("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));
        launch(args);
    }

}
