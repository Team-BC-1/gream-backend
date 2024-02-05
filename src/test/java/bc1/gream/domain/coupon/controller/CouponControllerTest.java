package bc1.gream.domain.coupon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.global.security.UserDetailsImpl;
import bc1.gream.global.security.WithMockCustomUser;
import bc1.gream.test.CouponTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CouponController.class)
@WithMockCustomUser
@ActiveProfiles("test")
class CouponControllerTest implements CouponTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    CouponQueryService couponQueryService;
    @MockBean
    BuyQueryService buyQueryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .alwaysDo(print()).build();
    }

    @Test
    void 로그인_한_유저가_사용가능한_쿠폰을_조회하는_컨트롤러_기능_성공_테스트() throws Exception {

        // given
        List<Coupon> couponList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            couponList.add((i % 2 == 0) ? TEST_COUPON_FIX : TEST_COUPON_RATE);
        }

        given(couponQueryService.availableCouponList(any(UserDetailsImpl.class))).willReturn(couponList);

        // when - then
        mockMvc.perform(get("/api/coupons"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(0),
                jsonPath("$.message").value("정상 처리 되었습니다"),
                jsonPath("$.data.size()").value(5),
                jsonPath("$.data[0].discountType").value("FIX"),
                jsonPath("$.data[1].discountType").value("RATE")
            );
    }

    @Test
    void unavailableCouponList() {
    }
}