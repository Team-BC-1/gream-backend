package bc1.gream.domain.sell.service.helper.deadline;

import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DeadlineCalculator {

    public static LocalDateTime getDeadlineOf(SellBidRequestDto requestDto) {
        Integer period = Deadline.getPeriod(requestDto.period());
        LocalDateTime deadlineAt = DeadlineCalculator.calculateDeadlineBy(LocalDate.now(), LocalTime.MAX,
            period);
        return deadlineAt;
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
