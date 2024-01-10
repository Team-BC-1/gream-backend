package bc1.gream.domain.product.controller;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.product.dto.ProductQueryResponseDto;
import bc1.gream.domain.product.dto.TradeResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.query.ProductOrderQueryService;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.global.common.RestResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/* 읽기 전용 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductQueryService productQueryService;
    private final ProductOrderQueryService productOrderQueryService;

    @GetMapping
    public RestResponse<List<ProductQueryResponseDto>> findAll() {
        List<Product> products = productQueryService.findAll();
        List<ProductQueryResponseDto> responseDtos = products.stream()
            .map(ProductMapper.INSTANCE::toQueryResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }

    @GetMapping("/{id}")
    public RestResponse<ProductQueryResponseDto> findAllBy(
        @PathVariable("id") Long productId
    ) {
        Product product = productQueryService.findBy(productId);
        ProductQueryResponseDto responseDto = ProductMapper.INSTANCE.toQueryResponseDto(product);
        return RestResponse.success(responseDto);
    }

    // 상품 :: 체결 거래 내역 조회
    @GetMapping("/{id}/trade")
    public RestResponse<List<TradeResponseDto>> findAllTrades(
        @PathVariable("id") Long productId
    ) {
        List<Order> allTrades = productOrderQueryService.findAllTradesOf(productId);
        List<TradeResponseDto> tradeResponseDtos = allTrades.stream()
            .map(OrderMapper.INSTANCE::toTradeResponseDto)
            .toList();
        return RestResponse.success(tradeResponseDtos);
    }

    // 상품 :: 판매 입찰가 조회
    @GetMapping("/{id}/sell")
    public RestResponse<ProductQueryResponseDto> findAllSoldBidPrices(
        @PathVariable("id") Long productId
    ) {
        return null;
    }

    // 상품 :: 구매 입찰가 조회
    @GetMapping("/{id}/buy")
    public RestResponse<ProductQueryResponseDto> findAllPurchasedBidPrices(
        @PathVariable("id") Long productId
    ) {
        return null;
    }
}