package nextstep.courses.domain.fixture;

import nextstep.courses.domain.enrollment.engine.SessionEnrollment;
import nextstep.courses.domain.enrollment.*;

import static nextstep.courses.domain.fixture.IdFixture.SESSION_ENROLLMENT_ID;
import static nextstep.courses.domain.fixture.IdFixture.SESSION_ID;

public class SessionEnrollmentFixture {

    public static FreeSessionEnrollment freeSessionEnrollment() {
        return new FreeSessionEnrollment(SESSION_ENROLLMENT_ID, SESSION_ID, SessionStatus.RECRUITING);
    }

    public static SessionEnrollment freeSessionEnrollment(SessionStatus status) {
        return new FreeSessionEnrollment(SESSION_ENROLLMENT_ID, SESSION_ID, status);
    }

    public static SessionEnrollment costSessionEnrollment(SessionStatus status, int capacity, long fee) {
        return new PaidSessionEnrollment(SESSION_ENROLLMENT_ID, SESSION_ID, status, capacity, fee);
    }

}
