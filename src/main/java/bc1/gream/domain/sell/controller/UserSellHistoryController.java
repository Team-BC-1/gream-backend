package bc1.gream.domain.sell.controller;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.sell.dto.response.OrderAsSellerResponseDto;
import bc1.gream.domain.sell.dto.response.UserSellOnProgressResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.mapper.SellMapper;
import bc1.gream.domain.sell.provider.SellerOrderProvider;
import bc1.gream.domain.sell.service.SellService;
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
@RequestMapping("/api/buy")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserSellHistoryController {

    private final SellerOrderProvider sellerOrderProvider;
    private final SellService sellService;


    @GetMapping("/end")
    public RestResponse<List<OrderAsSellerResponseDto>> getSoldOrderOf(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<Order> orders = sellerOrderProvider.getSoldOrderOf(userDetails.getUser());
        List<OrderAsSellerResponseDto> responseDtos = orders.stream()
            .map(OrderMapper.INSTANCE::toOrderAsSellerResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }

    @GetMapping("/onprogress")
    public RestResponse<List<UserSellOnProgressResponseDto>> getUserSellOnProgress(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<Sell> sellsOnProgress = sellService.getUserSellOnProgressOf(userDetails.getUser());
        List<UserSellOnProgressResponseDto> responseDtos = sellsOnProgress.stream()
            .map(SellMapper.INSTANCE::toUserSellOnProgressResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }
}
