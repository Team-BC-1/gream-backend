package bc1.gream.domain.sell.controller;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.sell.dto.response.UserSoldGifticonResponseDto;
import bc1.gream.domain.sell.provider.UserSoldGifticonProvider;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserSellHistoryController {

    private final UserSoldGifticonProvider userBoughtGifticonProvider;

    @GetMapping("/end")
    public RestResponse<List<UserSoldGifticonResponseDto>> getUserBoughtGifticon(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<Order> orders = userBoughtGifticonProvider.getBoughtGifticonOf(userDetails.getUser());
        List<UserSoldGifticonResponseDto> responseDtos = orders.stream()
            .map(OrderMapper.INSTANCE::toUserBoughtGifticonResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }
}
