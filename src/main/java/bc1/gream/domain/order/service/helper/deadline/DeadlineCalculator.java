package bc1.gream.domain.order.service.helper.deadline;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DeadlineCalculator {

    /**
     * 해당 날짜 date 의 시간대 time 로부터 마감일 period 후의 날짜 반환
     *
     * @param date   해당 날짜
     * @param time   시간대
     * @param period 마감일
     * @return 마감일자
     */
    public static LocalDateTime calculateDeadlineBy(LocalDate date, LocalTime time, Integer period) {
        return date.atTime(time).plusDays(period);
    }
}
