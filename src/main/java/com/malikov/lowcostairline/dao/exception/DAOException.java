package com.malikov.lowcostairline.dao.exception;

import java.sql.SQLException;

/**
 * @author Yurii Malikov
 */
public class DAOException extends RuntimeException {

    public DAOException(String message, SQLException e) {
        super(message,e);
    }
}
