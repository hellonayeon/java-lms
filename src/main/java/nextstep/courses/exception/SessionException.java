package nextstep.courses.exception;

import java.text.MessageFormat;

public class SessionException extends RuntimeException {

    public SessionException(SessionExceptionMessage exceptionMessage, String detailMessage) {
        super(MessageFormat.format("{0} ({1})", exceptionMessage.getMessage(), detailMessage));
    }

}
