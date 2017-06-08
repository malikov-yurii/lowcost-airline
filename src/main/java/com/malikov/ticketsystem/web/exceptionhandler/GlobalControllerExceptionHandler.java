package com.malikov.ticketsystem.web.exceptionhandler;

import com.malikov.ticketsystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static com.malikov.ticketsystem.util.MessageUtil.getMessage;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @Autowired
    MessageSource messageSource;

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOG.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView mav = new ModelAndView("exception");
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();

        if (rootMsg != null) {
            Optional<Map.Entry<String, String>> entry = ValidationUtil.constraintCodeMap.entrySet().stream()
                    .filter((it) -> rootMsg.contains(it.getKey()))
                    .findAny();
            entry.ifPresent(stringStringEntry ->
                    mav.addObject("exceptionSimpleMessage",
                            getMessage(messageSource, stringStringEntry.getValue())));
        }

        mav.addObject("exception", e);

        return mav;
    }
}
