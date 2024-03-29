package bc1.gream.domain.sell.controller;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.sell.dto.response.OrderAsSellerResponseDto;
import bc1.gream.domain.sell.dto.response.UserSellOnProgressResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.mapper.SellMapper;
import bc1.gream.domain.sell.provider.SellerOrderProvider;
import bc1.gream.domain.sell.service.query.SellQueryService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/sell/history")
@RequiredArgsConstructor
public class UserSellHistoryController {

    private final SellerOrderProvider sellerOrderProvider;
    private final SellQueryService sellQueryService;

    /**
     * 판매자 판매내역 히스토리 조회 요청
     *
     * @param userDetails 판매자
     * @return 판매내역 목록
     */
    @GetMapping("/end")
    @Operation(summary = "판매내역 히스토리 조회 요청", description = "판매자의 판매완료 내역 조회요청을 처리합니다.")
    public RestResponse<List<OrderAsSellerResponseDto>> getSoldOrderOf(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<Order> orders = sellerOrderProvider.getSoldOrderOf(userDetails.getUser());
        List<OrderAsSellerResponseDto> responseDtos = orders.stream()
            .map(OrderMapper.INSTANCE::toOrderAsSellerResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }

    /**
     * 판매자 판매입찰 히스토리 조회 요청
     *
     * @param userDetails 판매자
     * @return 판매입찰 목록
     */
    @GetMapping("/onprogress")
    @Operation(summary = "판매입찰 히스토리 조회 요청", description = "진행 중인 판매자의 판매입찰 내역 조회요청을 처리합니다.")
    public RestResponse<List<UserSellOnProgressResponseDto>> getUserSellOnProgress(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<Sell> sellsOnProgress = sellQueryService.getUserSellOnProgressOf(userDetails.getUser());
        List<UserSellOnProgressResponseDto> responseDtos = sellsOnProgress.stream()
            .map(SellMapper.INSTANCE::toUserSellOnProgressResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }
}
