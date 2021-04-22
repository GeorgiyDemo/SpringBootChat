package com.demka.demkaserver.config;

import com.demka.demkaserver.services.LongPollService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type My initializing bean.
 */
@Component
public class MyInitializingBean implements InitializingBean {

    private final LongPollService longPollService;

    /**
     * Instantiates a new My initializing bean.
     *
     * @param longPollService the long poll service
     */
    @Autowired
    public MyInitializingBean(LongPollService longPollService) {
        this.longPollService = longPollService;
    }

    /**
     * Переопределение нужно для того, чтоб удалять данные старых лонгпулов с СУБД
     * После реинициализации спринга
     */
    @Override
    public void afterPropertiesSet() {
        longPollService.deleteAll();
    }
}
