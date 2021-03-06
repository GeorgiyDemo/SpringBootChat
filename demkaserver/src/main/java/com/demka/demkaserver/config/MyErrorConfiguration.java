package com.demka.demkaserver.config;

import com.demka.demkaserver.controllers.MyErrorController;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * The type My error configuration.
 */
@Configuration
public class MyErrorConfiguration {

    /**
     * Нужен, чтоб кастомный ErrorController использовать
     * А он в свою очередь нужен, чтоб доп атрибут result при ошибке выводить
     *
     * @param errorAttributes            the error attributes
     * @param serverProperties           the server properties
     * @param errorViewResolversProvider the error view resolvers provider
     * @return the my error controller
     */
    @Bean
    public MyErrorController ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
        return new MyErrorController(errorAttributes, serverProperties.getError(), errorViewResolversProvider.getIfAvailable());
    }
}
