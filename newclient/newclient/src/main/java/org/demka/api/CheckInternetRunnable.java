package org.demka.api;

import javafx.application.Platform;
import org.demka.App;
import org.demka.controllers.ConnectionErrorController;
import org.demka.exceptions.EmptyAPIResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CheckInternetRunnable implements Runnable {


      private static final Logger logger = LoggerFactory.getLogger(CheckInternetRunnable.class);
      private App app;
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


            while (!Thread.currentThread().isInterrupted()){
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
                        Thread.currentThread().interrupt();
                        logger.error("Поток '"+Thread.currentThread().getName()+"' с id "+Thread.currentThread().getId()+" убит");
                        return;
                  }

                  //Если интернет появился, то обратно переходим в чат
                  if ((!exceptionFlag) && (ConnectionErrorController.isActive)){
                        ConnectionErrorController.isActive = false;
                        Platform.runLater(() -> app.myStart(app.getPrimaryStage()));
                        RunnableManager.interruptAll();
                        break;
                  }
            }

            //Переходим отбратно в чат, а данный поток завершает свою работу
            logger.error("Поток '"+Thread.currentThread().getName()+"' с id "+Thread.currentThread().getId()+" завершил работу");
      }
}