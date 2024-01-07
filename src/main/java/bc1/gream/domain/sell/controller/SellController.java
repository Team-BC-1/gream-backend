package bc1.gream.domain.sell.controller;

import bc1.gream.domain.sell.dto.request.SellRequestDto;
import bc1.gream.domain.sell.dto.response.SellResponseDto;
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
    public RestResponse<SellResponseDto> sellProduct(
        User user,
        @Valid @RequestBody SellRequestDto requestDto,
        @PathVariable Long productId
    ) {
        SellResponseDto response = sellService.sellNowProduct(user, requestDto, productId);
        return RestResponse.success(response);
    }
}
