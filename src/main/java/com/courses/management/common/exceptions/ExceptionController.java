package com.courses.management.common.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    private static final Logger LOG = LogManager.getLogger(ExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception ex) {
        LOG.error("handleException: ", ex);
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", ex.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }
}
