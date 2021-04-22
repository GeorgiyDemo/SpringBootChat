package com.demka.demkaserver.controllers;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * The type My error controller.
 */
public class MyErrorController extends BasicErrorController {

    /**
     * Instantiates a new My error controller.
     *
     * @param errorAttributes    the error attributes
     * @param errorProperties    the error properties
     * @param errorViewResolvers the error view resolvers
     */
    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }


    /**
     * Переопределение полей, которые отдаются при ошибке обработки запроса
     *
     * @param request
     * @param options
     * @return
     */
    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        errorAttributes.put("result", false);
        return errorAttributes;
    }

}


