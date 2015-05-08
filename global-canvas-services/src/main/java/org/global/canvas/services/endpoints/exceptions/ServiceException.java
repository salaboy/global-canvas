package org.global.canvas.services.endpoints.exceptions;

import java.io.Serializable;

/**
 * Created by salaboy on 27/04/15.
 */
public class ServiceException extends Exception implements Serializable {

    private boolean isSecurityRelated = false;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(boolean isSecurityRelated) {
        super();
        this.isSecurityRelated = isSecurityRelated;
    }

    public ServiceException(String message, boolean isSecurityRelated) {
        super(message);
        this.isSecurityRelated = isSecurityRelated;
    }

    public ServiceException(String message, Throwable cause, boolean isSecurityRelated) {
        super(message, cause);
        this.isSecurityRelated = isSecurityRelated;
    }

    public boolean isIsSecurityRelated() {
        return isSecurityRelated;
    }

}
