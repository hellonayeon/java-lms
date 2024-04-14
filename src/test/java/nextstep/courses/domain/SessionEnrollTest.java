package nextstep.courses.domain;

import nextstep.courses.domain.enrollment.engine.SessionEnroll;
import nextstep.courses.exception.SessionCapacityExceedException;
import nextstep.courses.exception.SessionFeeMismatchException;
import nextstep.courses.exception.SessionStatusCannotEnrollmentException;
import nextstep.payments.domain.Payment;
import nextstep.payments.exception.PaymentAmountExistException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.enrollment.SessionStatus.FINISHED;
import static nextstep.courses.domain.enrollment.SessionStatus.RECRUITING;
import static nextstep.courses.domain.fixture.NsUserFixture.nsUser;
import static nextstep.courses.domain.fixture.PaymentFixture.payment;
import static nextstep.courses.domain.fixture.SessionEnrollmentFixture.freeSessionEnrollment;
import static nextstep.courses.domain.fixture.SessionEnrollmentFixture.paidSessionEnrollment;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class SessionEnrollTest {

    @Test
    @DisplayName("[성공] 무료 강의 신청을 만족한다.")
    void 무료_강의_신청_만족() {
        SessionEnroll enrollment = freeSessionEnrollment(RECRUITING);

        Payment payment = payment(0L);

        assertThatNoException().isThrownBy(() -> enrollment.satisfyEnrollment(payment));
    }

    @Test
    @DisplayName("[실패] 무료 강의 신청 시 결제 금액이 존재한다면 PaymentAmountExistException 예외가 발생한다.")
    void 무료_강의_신청_결제_금액_존재() {
        SessionEnroll enrollment = freeSessionEnrollment(RECRUITING);

        Payment payment = payment(1L);

        assertThatExceptionOfType(PaymentAmountExistException.class)
                .isThrownBy(() -> enrollment.satisfyFee(payment));
    }

    @Test
    @DisplayName("[성공] 유료 강의 신청을 만족한다.")
    void 유료_강의_신청_만족() {
        SessionEnroll enrollment = paidSessionEnrollment(RECRUITING, 10, 800_000L);

        Payment payment = payment(800_000L);

        assertThatNoException().isThrownBy(() -> enrollment.satisfyEnrollment(payment));
    }

    @Test
    @DisplayName("[실패] 강의 신청 시 수강신청 가능한 상태가 아니라면 SessionCapacityExceedException 예외가 발생한다.")
    void 강의_신청_수강신청_불가능() {
        SessionEnroll enrollment = freeSessionEnrollment(FINISHED);

        assertThatExceptionOfType(SessionStatusCannotEnrollmentException.class)
                .isThrownBy(enrollment::satisfyStatus);
    }

    @Test
    @DisplayName("[실패] 유료 강의 신청 시 결제 수강 인원이 초과 된다면 SessionCapacityExceedException 예외가 발생한다.")
    void 유료_강의_신청_수강_인원_초과() {
        SessionEnroll enrollment = paidSessionEnrollment(RECRUITING, 0, 800_000L);

        assertThatExceptionOfType(SessionCapacityExceedException.class)
                .isThrownBy(enrollment::satisfyCapacity);
    }

    @Test
    @DisplayName("[실패] 유료 강의 신청 시 결제 금액이 수강료와 불일치 한다면 SessionFeeMismatchException 예외가 발생한다.")
    void 유료_강의_신청_결제_금액_불일치() {
        SessionEnroll enrollment = paidSessionEnrollment(RECRUITING, 0, 800_000L);
        Payment payment = payment(100_000L);

        assertThatExceptionOfType(SessionFeeMismatchException.class)
                .isThrownBy(() -> enrollment.satisfyFee(payment));
    }

    @Test
    @DisplayName("[성공] 무료 강의를 신청한다.")
    void 무료_강의_신청() {
        SessionEnroll enrollment = freeSessionEnrollment(RECRUITING);

        NsUser user = nsUser();
        Payment payment = payment(0L);

        assertThatNoException()
                .isThrownBy(() -> enrollment.enroll(user, payment));
    }

    @Test
    @DisplayName("[성공] 유료 강의를 신청한다.")
    void 유료_강의_신청() {
        SessionEnroll enrollment = paidSessionEnrollment(RECRUITING, 10, 800_000L);

        NsUser user = nsUser();
        Payment payment = payment(800_000L);

        assertThatNoException()
                .isThrownBy(() -> enrollment.enroll(user, payment));
    }

}
