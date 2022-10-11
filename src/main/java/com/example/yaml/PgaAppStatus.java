package com.example.yaml;

public enum PgaAppStatus {
    SUBMIT,
    UPDATE,
    RECEIVE,
    PROC,
    RETURN,
    DEFERRED,
    REJECT,
    ISSUE_WAITING,
    APPROVE,
    ISSUE_PENDING,
    COMPLETE,
    WITHDRAW,
    ENDORSE_WAITING,
    ENDORSE_PENDING,
    ENDORSE,
    CONFIRM,
    AMEND,
    VOID;

    private PgaAppStatus() {
    }
}
