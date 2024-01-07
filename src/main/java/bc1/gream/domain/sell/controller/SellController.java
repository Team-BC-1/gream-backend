package bc1.gream.domain.sell.controller;

import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.service.SellService;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sell")
public class SellController {

    private final SellService sellService;

    @PostMapping("/{productId}/now")
    public RestResponse<SellNowResponseDto> sellNowProduct(
        User user,
        @Valid @RequestBody SellNowRequestDto requestDto,
        @PathVariable Long productId
    ) {
        SellNowResponseDto response = sellService.sellNowProduct(user, requestDto, productId);
        return RestResponse.success(response);
    }

    @PostMapping("/{productId}")
    public RestResponse<SellBidResponseDto> sellBidProduct(
        User user,
        @Valid @RequestBody SellBidRequestDto requestDto,
        @PathVariable Long productId
    ) {
        SellBidResponseDto response = sellService.sellBidProduct(user, requestDto, productId);
        return RestResponse.success(response);
    }
}
