package com.demka.demkaserver.config;

import com.demka.demkaserver.services.LongPollService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyInitializingBean implements InitializingBean {

    @Autowired
    private LongPollService longPollService;

    /**
     * Кароч это переопределение нужно для того, чтоб удалять данные старых лонгпулов с СУБД
     * После реинициализации спринга
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        longPollService.deleteAll();
    }
}