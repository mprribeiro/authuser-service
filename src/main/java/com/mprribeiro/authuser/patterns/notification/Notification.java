package com.mprribeiro.authuser.patterns.notification;

import java.util.ArrayList;
import java.util.List;

public class Notification {

    private List<String> errors = new ArrayList<>();

    public void addError(String error) {
        this.errors.add(error);
    }

    public boolean hasError() {
        return !this.errors.isEmpty();
    }

    @Override
    public String toString() {
        return String.join(", ", this.errors);
    }
}
