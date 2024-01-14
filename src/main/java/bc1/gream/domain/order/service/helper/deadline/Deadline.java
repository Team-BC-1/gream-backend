package bc1.gream.domain.order.service.helper.deadline;

import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Deadline {
    WEEK(7);

    private final int period;

    /**
     * 기간설정값이 있다면 해당값 반환, 없다면 WEEK 반환
     *
     * @param period 기간설정값
     * @return 기간
     */
    public static Integer getPeriod(Integer period) {
        return Objects.requireNonNullElse(period, WEEK.period);
    }
}
