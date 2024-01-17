package bc1.gream.domain.product.controller;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.domain.product.dto.response.OrderTradeResponseDto;
import bc1.gream.domain.product.dto.response.ProductDetailsResponseDto;
import bc1.gream.domain.product.dto.response.ProductPreviewResponseDto;
import bc1.gream.domain.product.dto.response.SellPriceToQuantityResponseDto;
import bc1.gream.domain.product.dto.unit.ProductCondition;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.provider.BuyOrderQueryProvider;
import bc1.gream.domain.product.provider.SellOrderQueryProvider;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.provider.ProductOrderQueryProvider;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.validator.OrderCriteriaValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 읽기 전용
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductService productService;
    private final ProductOrderQueryProvider productOrderQueryProvider;
    private final SellOrderQueryProvider sellOrderQueryProvider;
    private final BuyOrderQueryProvider buyOrderQueryProvider;

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public RestResponse<List<ProductPreviewResponseDto>> findAll(
        @RequestParam(required = false) ProductCondition productCondition
    ) {
        List<Product> products = productService.findAllBy(productCondition);
        List<ProductPreviewResponseDto> responseDtos = products.stream()
            .map(ProductMapper.INSTANCE::toPreviewResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }

    /**
     * 상품 상세조회
     */
    @GetMapping("/{id}")
    public RestResponse<ProductDetailsResponseDto> findAllBy(
        @PathVariable("id") Long productId
    ) {
        Product product = productService.findBy(productId);
        ProductDetailsResponseDto responseDto = ProductMapper.INSTANCE.toDetailsResponseDto(product);
        return RestResponse.success(responseDto);
    }

    /**
     * 상품 체결내역 조회
     */
    @GetMapping("/{id}/trade")
    public RestResponse<List<OrderTradeResponseDto>> findAllTrades(
        @PathVariable("id") Long productId
    ) {
        List<Order> allTrades = productOrderQueryProvider.findAllTradesOf(productId);
        List<OrderTradeResponseDto> orderTradeResponseDtos = allTrades.stream()
            .map(OrderMapper.INSTANCE::toOrderTradeResponseDto)
            .toList();
        return RestResponse.success(orderTradeResponseDtos);
    }

    /**
     * 판매 입찰가 조회
     */
    @GetMapping("/{id}/sell")
    public RestResponse<List<SellPriceToQuantityResponseDto>> findAllSellBidPrices(
        @PathVariable("id") Long productId,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Sell.class, pageable);
        List<SellPriceToQuantityResponseDto> sellPriceToQuantityResponseDtos = sellOrderQueryProvider.findAllSellBidsOf(productId,
            pageable);
        return RestResponse.success(sellPriceToQuantityResponseDtos);
    }

    /**
     * 구매 입찰가 조회
     */
    @GetMapping("/{id}/buy")
    public RestResponse<List<BuyPriceToQuantityResponseDto>> findAllBuyBidPrices(
        @PathVariable("id") Long productId,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Buy.class, pageable);
        List<BuyPriceToQuantityResponseDto> buyPriceToQuantityResponseDtos = buyOrderQueryProvider.findAllBuyBidsOf(productId, pageable);
        return RestResponse.success(buyPriceToQuantityResponseDtos);
    }
}