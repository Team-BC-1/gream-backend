package bc1.gream.domain.gifticon.service.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.test.GifticonTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GifticonCommandServiceTest implements GifticonTest {

    @InjectMocks
    private GifticonCommandService gifticonCommandService;
    @Mock
    private GifticonRepository gifticonRepository;

    @Test
    void 새로운_기프티콘을_등록하는_서비스_기능_성공_테스트() {
        // given - when
        gifticonCommandService.saveGifticon(TEST_GIFTICON_URL, TEST_ORDER);

        // then
        verify(gifticonRepository, times(1)).save(any(Gifticon.class));
    }
}