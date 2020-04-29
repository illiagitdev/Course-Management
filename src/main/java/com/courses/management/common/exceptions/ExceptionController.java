package com.courses.management.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", ex.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }
}
