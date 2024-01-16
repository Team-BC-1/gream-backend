package bc1.gream.test;

import bc1.gream.domain.order.entity.Gifticon;

public interface GifticonTest extends OrderTest {

    Long TEST_GIFTICON_ID = 1L;

    String TEST_GIFTICON_URL = "images/images.png";

    Gifticon TEST_GIFTICON_END = Gifticon.builder()
        .gifticonUrl(TEST_GIFTICON_URL)
        .order(TEST_ORDER)
        .build();

    Gifticon TEST_GIFTICON = Gifticon.builder()
        .gifticonUrl(TEST_GIFTICON_URL)
        .order(null)
        .build();
}
