package bc1.gream.domain.sell.service.helper.deadline;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DeadlineCalculator {

    /**
     * 현재일자로부터 입력주기에 따른 마감일자를 반환
     *
     * @param period 입력주기
     * @return 마감일자
     */
    public static LocalDateTime getDeadlineOf(Integer period) {
        Integer calculatedPeriod = Deadline.getPeriod(period);
        return calculateDeadlineBy(LocalDate.now(), LocalTime.MAX, calculatedPeriod);
    }

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
