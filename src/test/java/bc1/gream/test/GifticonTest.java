package bc1.gream.test;

import bc1.gream.domain.order.entity.Gifticon;

public interface GifticonTest extends SellTest, OrderTest {

    Gifticon TEST_GIFTICON = Gifticon.builder()
        .gifticonUrl(TEST_GIFTICON_URL)
        .sell(TEST_SELL)
        .order(TEST_ORDER)
        .build();
}
