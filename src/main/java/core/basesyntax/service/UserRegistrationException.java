package core.basesyntax.service;

import java.io.IOException;

public class UserRegistrationException extends IOException {
    public UserRegistrationException(String message) {
        super(message);
    }
}