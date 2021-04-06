package org.demka.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CheckInternetRunnable implements Runnable {

      private static final Logger logger = LoggerFactory.getLogger(CheckInternetRunnable.class);

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

                  try {
                        URL url = new URL("http://www.google.com");
                        URLConnection connection = url.openConnection();
                        connection.setConnectTimeout(2000);
                        connection.connect();
                        logger.info("Интернет есть, все хорошо");
                  } catch (IOException e) {
                        logger.info("Интернета нет, все плохо");
                        LongPollRunnable.IsInternetAvailable = false;
                  }


                  //Спим
                  try {
                        Thread.sleep(250);
                  } catch (InterruptedException e) {
                        e.printStackTrace();
                        logger.error("CheckInternetRunnable - Прекратил работу");
                        return;
                  }
            }

      }
}