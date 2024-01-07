package bc1.gream.test;

import bc1.gream.domain.sell.entity.Gifticon;

public interface GifticonTest extends SellTest {

    Long TEST_GIFTICON_ID = 1L;

    String TEST_GIFTICON_IMG = "C:\\";

    Gifticon TEST_GIFTICON = Gifticon.builder()
        .gifticonImg(TEST_GIFTICON_IMG)
        .sell(TEST_SELL)
        .build();
}
