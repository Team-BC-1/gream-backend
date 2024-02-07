package bc1.gream.domain.buy.provider;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.global.security.WithMockCustomUser;
import bc1.gream.test.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@WithMockCustomUser
@Disabled
@ActiveProfiles("test")
class BuyBidProviderIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BuyBidProvider buyBidProvider;

    @BeforeEach
    void setUp() {
        setUpBaseIntegrationTest();
    }

    @AfterEach
    void tearDown() {
        tearDownBaseIntegrationTest();
    }

    @Test
    @Transactional
    public void 구매입찰생성요청() {
        // GIVEN
        BuyBidRequestDto buyBidRequestDto = BuyBidRequestDto.builder()
            .price(1000L)
            .period(7)
            .build();

        // WHEN
        BuyBidResponseDto responseDto = buyBidProvider.buyBidProduct(savedBuyer, buyBidRequestDto, savedIcedAmericano);

        // THEN
        System.out.println("responseDto.toString() = " + responseDto.toString());
    }
}