package bc1.gream.domain.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bc1.gream.domain.admin.dto.request.AdminCreateCouponRequestDto;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.entity.DiscountType;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.user.entity.UserRole;
import bc1.gream.global.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = AdminController.class)
@WithMockCustomUser(loginId = "adminId12", password = "adminPw12!", userRole = UserRole.ADMIN)
@ActiveProfiles("test")
class AdminControllerTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CouponCommandService couponCommandService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }

    @Test
    public void 새로운_쿠폰_추가_정상요청() throws Exception {
        // GIVEN
        String url = "/api/admin/coupon";
        AdminCreateCouponRequestDto requestDto = new AdminCreateCouponRequestDto("쿠폰이름", DiscountType.FIX, 1000L, CouponStatus.AVAILABLE);

        // WHEN
        // THEN
        mockMvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
    }

    @Test
    public void 새로운_쿠폰_추가_유효하지_않은_요청() throws Exception {
        // GIVEN
        String url = "/api/admin/coupon";
        AdminCreateCouponRequestDto requestDtoOfNull = new AdminCreateCouponRequestDto(null, null, null, null);
        AdminCreateCouponRequestDto requestDtoOfNameOutOfSize = new AdminCreateCouponRequestDto("01234567890123456789012345678901234567891",
            DiscountType.FIX, 1000L, CouponStatus.AVAILABLE);
        AdminCreateCouponRequestDto requestDtoOfDiscountZero = new AdminCreateCouponRequestDto("쿠폰이름", DiscountType.FIX, 0L,
            CouponStatus.AVAILABLE);
        AdminCreateCouponRequestDto requestDtoOfDiscountNegative = new AdminCreateCouponRequestDto("쿠폰이름", DiscountType.FIX, -1000L,
            CouponStatus.AVAILABLE);

        // WHEN
        // THEN
        mockMvc.perform(
                post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDtoOfNull))
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
        mockMvc.perform(
                post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDtoOfNameOutOfSize))
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
        mockMvc.perform(
                post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDtoOfDiscountZero))
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
        mockMvc.perform(
                post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDtoOfDiscountNegative))
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }
}