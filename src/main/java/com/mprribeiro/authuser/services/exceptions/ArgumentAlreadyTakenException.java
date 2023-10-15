package com.mprribeiro.authuser.services.exceptions;

public class ArgumentAlreadyTakenException extends RuntimeException {

    public ArgumentAlreadyTakenException(String msg) {
        super(msg);
    }
}
