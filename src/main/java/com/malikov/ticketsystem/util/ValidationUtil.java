package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.IHasId;
import com.malikov.ticketsystem.util.exception.NotFoundException;

/**
 * @author Yurii Malikov
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, long id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, long id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(IHasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " should be new (id==null or id=0)");
        }
    }

    public static void checkIdConsistent(IHasId bean, long id) {
        if (bean.isNew()) {
            // TODO: 6/2/2017 Why I need this part??
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " should be with id=" + id);
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
