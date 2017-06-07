package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.IHasId;
import com.malikov.ticketsystem.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yurii Malikov
 */
public class ValidationUtil {

    public static Map<String, String> constraintCodeMap = new HashMap<String, String>() {
        {
            // TODO: 6/5/2017 i must use resource bundle instead!!
            put("email", "Sorry, inputted email is not free. Choose another.");
            put("seat", "Sorry, picked seat just has been booked by another user.");
        }
    };

    private ValidationUtil() {
    }

    public static void checkNotFoundById(boolean found, long id) {
        checkNotFound(found, "not found with id=" + id);
    }

    public static <T> T checkNotFoundById(T object, long id) {
        return checkNotFound(object, "not found with id=" + id);
    }

    public static <T> T checkNotFoundByName(T object, String name) {
        return checkNotFound(object, "not found by name=" + name);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }
    public static void checkNotFound(boolean found, String message) {
        if (!found) {
            throw new NotFoundException(message);
        }
    }

    public static void validate(boolean valid, String message) {
        if (!valid) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotNew(IHasId bean) {
        if (bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be not new (id!=null && id!=0)");
        }
    }

    public static void checkNew(IHasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id==null or id=0)");
        }
    }

    public static <T> void checkNotSame(T firstObject, T secondObject) {
        if (firstObject != null && secondObject != null) {
            validate(!firstObject.equals(secondObject), "must not be same");
        }
    }

    public static <T> void checkSame(T firstObject, T secondObject) {
        if (firstObject != null && secondObject != null) {
            validate(firstObject.equals(secondObject), "must be same");
        }
    }

    public static void validateFromToDates(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        if (fromDateTime != null && toDateTime != null) {
            validate(fromDateTime.compareTo(toDateTime) < 0,
                    "\"from datetime\" cannot be after \"to\" datetime");
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
