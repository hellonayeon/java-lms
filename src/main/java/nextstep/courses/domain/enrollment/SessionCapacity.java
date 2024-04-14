package nextstep.courses.domain.enrollment;

import nextstep.courses.exception.SessionCapacityInvalidException;

public class SessionCapacity {

    public static final int MIN_CAPACITY = 0;
    public static final int INFINITY = Integer.MAX_VALUE;

    private Long sessionId;
    private final int capacity;

    public SessionCapacity(Long sessionId, int capacity) {
        validate(capacity);
        this.sessionId = sessionId;
        this.capacity = capacity;
    }

    public SessionCapacity(int capacity) {
        validate(capacity);
        this.capacity = capacity;
    }

    private void validate(int capacity) {
        if (capacity < MIN_CAPACITY) {
            throw new SessionCapacityInvalidException(capacity);
        }
    }

    public boolean noCapacity(int currentSize) {
        return capacity <= currentSize;
    }

    public int get() {
        return capacity;
    }

}
