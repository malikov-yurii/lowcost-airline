package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.IHasId;
import com.malikov.ticketsystem.util.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yurii Malikov
 */
public class ValidationUtil {

    public static Map<String, String> constraintCodeMap = new HashMap<String, String>() {
        {
            // TODO: 6/5/2017 i should use resource bundle instead!!
            put("email", "Sorry, inputted email is not free. Choose another.");
            put("seat", "Sorry, picked seat just has been booked by another user.");
        }
    };

    private ValidationUtil() {
    }

    public static void checkNotFoundById(boolean found, long id) {
        checkSuccess(found, "not found with id=" + id);
    }

    public static <T> T checkNotFoundById(T object, long id) {
        return checkSuccess(object, "not found with id=" + id);
    }

    public static <T> T checkNotFoundByName(T object, String criteria) {
        return checkSuccess(object, "not found by name=" + criteria);
    }

    public static <T> T checkSuccess(T object, String msg) {
        checkSuccess(object != null, msg);
        return object;
    }

    public static void checkSuccess(boolean found, String message) {
        if (!found) {
            throw new NotFoundException(message);
        }
    }

    public static void checkNotNew(IHasId bean) {
        if (bean.isNew()) {
            throw new IllegalArgumentException(bean + " should be not new (id!=null && id!=0)");
        }
    }

    public static void checkNew(IHasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " should be new (id==null or id=0)");
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
