package com.pironline.test.exceptions;

import com.pironline.test.exceptions.handler.ErrorCode;
import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    private final Object[] args;
    private final ErrorCode errorCode;

    public GenericException(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public GenericException(ErrorCode errorCode, Object[] args) {
        this(errorCode, null, args);
    }

    public GenericException(ErrorCode errorCode, Throwable cause){
        this(errorCode, cause, null);
    }

    public GenericException(ErrorCode errorCode, Throwable cause, Object[] args) {
        super(errorCode.getValue(), cause);
        this.args = args;
        this.errorCode = errorCode;
    }
}
