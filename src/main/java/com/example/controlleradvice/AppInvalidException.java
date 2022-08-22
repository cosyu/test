package com.example.controlleradvice;

import org.springframework.validation.FieldError;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;

public class AppInvalidException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private  Integer errCode;

    //private final String[] errMsgArgs;
    private  String errMessage;

    private  Status httpStatus;

    private  List<FieldError> fieldErrors;

    public AppInvalidException(Integer errCode, List<FieldError> fieldErrors) {
        this.errCode = errCode;
        this.fieldErrors = fieldErrors;
        //this.errMsgArgs = new String[] {};
        this.httpStatus = Status.BAD_REQUEST;
    }

    public AppInvalidException(Integer errCode, String errMessage) {
        super(null, errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.httpStatus = Status.FORBIDDEN;
        this.fieldErrors = new ArrayList<>();
    }

    public AppInvalidException(Integer errCode, Status httpStatus, String errMessage) {
        super(null, errMessage);

        this.errCode = errCode;
        //this.errMsgArgs = errMsgArgs;
        this.errMessage = errMessage;
        this.httpStatus = httpStatus;
        this.fieldErrors = new ArrayList<>();
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public Status getHttpStatus() {
        return httpStatus;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

}
