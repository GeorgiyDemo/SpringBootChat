package org.demka.api;

import javafx.application.Platform;
import org.demka.App;
import org.demka.controllers.ConnectionErrorController;
import org.demka.exceptions.EmptyAPIResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CheckInternetRunnable implements Runnable {

      private static final Logger logger = LoggerFactory.getLogger(CheckInternetRunnable.class);
      App app;
      public CheckInternetRunnable(App app){
            this.app = app;
      }
      /**
       * When an object implementing interface {@code Runnable} is used
       * to create a thread, starting the thread causes the object's
       * {@code run} method to be called in that separately executing
       * thread.
       * <p>
       * The general contract of the method {@code run} is that it may
       * take any action whatsoever.
       *
       * @see Thread#run()
       */
      @Override
      public void run() {
            while (true){
                  //Проверяем интернет

                  boolean exceptionFlag = false;
                  try {
                        URL url = new URL("http://www.google.com");
                        URLConnection connection = url.openConnection();
                        connection.setConnectTimeout(2000);
                        connection.connect();
                        logger.info("Интернет есть, все хорошо");
                  } catch (IOException e) {
                        logger.info("Интернета нет, все плохо");
                        try {
                              exceptionFlag = true;
                              //Вызываем страницу с ошибкой интернет-соединения, если еще не вызвали
                              if (!ConnectionErrorController.isActive) {
                                    throw new EmptyAPIResponseException(app, "нет интернет-соединения");
                              }
                        } catch (EmptyAPIResponseException emptyAPIResponseException) {
                              emptyAPIResponseException.printStackTrace();
                        }
                  }


                  //Спим
                  try {
                        Thread.sleep(1000);
                  } catch (InterruptedException e) {
                        e.printStackTrace();
                        logger.error("CheckInternetRunnable - Прекратил работу");
                        return;
                  }

                  //Если интернет появился, то обратно переходим в чат
                  if ((!exceptionFlag) && (ConnectionErrorController.isActive)){
                        ConnectionErrorController.isActive = false;
                        break;
                  }
            }

            //Переходим отбратно в чат, а данный поток завершает свою работу
            Platform.runLater(() -> app.myStart(app.getPrimaryStage()));
            logger.info("Поток '"+Thread.currentThread().getName()+"' завершил свою работу");
      }
}