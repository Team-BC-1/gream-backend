package bc1.gream.domain.gifticon.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.GifticonTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GifticonQueryServiceTest implements GifticonTest {

    @Mock
    GifticonRepository gifticonRepository;
    @InjectMocks
    private GifticonQueryService gifticonQueryService;

    @Test
    void 판매자_기준_판매가_완료된_기프티콘_전체_조회하는_서비스_기능_성공_테스트() {
        // given
        List<Gifticon> gifticonList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            gifticonList.add(TEST_GIFTICON);
        }

        given(gifticonRepository.findAllSoldBySeller(any(User.class))).willReturn(gifticonList);

        // when
        List<Gifticon> resultList = gifticonQueryService.findAllSoldBySeller(TEST_USER);

        // then
        assertThat(resultList.get(0)).isEqualTo(gifticonList.get(0));
        assertThat(resultList.get(1)).isEqualTo(gifticonList.get(1));
        assertThat(resultList.get(2)).isEqualTo(gifticonList.get(2));
        assertThat(resultList.get(3)).isEqualTo(gifticonList.get(3));
    }

    @Test
    void getBoughtOrder() {
    }
}