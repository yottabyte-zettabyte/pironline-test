package com.pironline.test.exceptions;

import com.pironline.test.exceptions.handler.ErrorCode;

public class OptimisticLockException extends GenericException {

    public OptimisticLockException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OptimisticLockException(ErrorCode errorCode, Object[] args) {
        super(errorCode, args);
    }
}
