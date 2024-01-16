package bc1.gream.domain.gifticon.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.test.BaseDataRepositoryTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GifticonRepositoryCustomImplTest extends BaseDataRepositoryTest {

    @Autowired
    private GifticonRepositoryCustomImpl gifticonRepositoryCustom;

    @BeforeEach
    void setUp() {
        setUpBaseDataRepositoryTest();
    }

    @Test
    @DisplayName("사용자가 판매자로서 판매한 기프티콘을 조회합니다.")
    public void 판매자_판매기프티콘_조회() {
        // WHEN
        List<Gifticon> allSoldBySeller = gifticonRepositoryCustom.findAllSoldBySeller(savedSeller);

        // THEN
        boolean isSoldGifticonHasSeller = allSoldBySeller.stream()
            .anyMatch(gifticon -> gifticon.getOrder()
                .getSeller()
                .equals(savedSeller));
        assertTrue(isSoldGifticonHasSeller);
    }
}