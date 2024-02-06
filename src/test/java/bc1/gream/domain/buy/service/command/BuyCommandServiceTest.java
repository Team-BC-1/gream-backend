package bc1.gream.domain.buy.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.test.BuyTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuyCommandServiceTest implements BuyTest {

    @InjectMocks
    private BuyCommandService buyCommandService;

    @Mock
    private BuyRepository buyRepository;

    @Test
    void 구매_아이디와_유저로_구매입찰_취소하는_서비스_기능_성공_테스트() {

        // given - when
        buyCommandService.deleteBuyByIdAndUser(TEST_BUY, TEST_USER);

        // then
        verify(buyRepository, times(1)).delete(any(Buy.class));
    }

    @Test
    void 구매_아이디와_유저로_구매입찰_취소하는_서비스_기능_실패_테스트() {

        // given - when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            buyCommandService.deleteBuyByIdAndUser(TEST_BUY, TEST_BUYER);
        });

        // then
        assertThat(exception.getResultCase()).isEqualTo(ResultCase.NOT_AUTHORIZED);
        assertThat(exception.getResultCase().getCode()).isEqualTo(5000);
        assertThat(exception.getResultCase().getMessage()).isEqualTo("해당 요청에 대한 권한이 없습니다.");
    }


    @Test
    void 구매입찰_취소하는_서비스_기능_성공_테스트() {

        // given - when
        buyCommandService.delete(TEST_BUY);

        // then
        verify(buyRepository, times(1)).delete(any(Buy.class));
    }

    @Test
    void 구매자가_정한_기한이_지난_구매입찰_삭제_서비스_성공_테스트() {

        // given - when
        buyCommandService.deleteBuysOfDeadlineBefore(TEST_DEADLINE_AT);

        // then
        verify(buyRepository, times(1)).deleteBuysOfDeadlineBefore(any(LocalDateTime.class));
    }
}