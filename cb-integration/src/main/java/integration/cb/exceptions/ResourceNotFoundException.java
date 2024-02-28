package integration.cb.exceptions;

import static java.lang.String.format;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message, Object... messageArguments) {
        super(format(message, messageArguments));
    }

}