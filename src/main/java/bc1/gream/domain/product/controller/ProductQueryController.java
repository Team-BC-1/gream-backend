package bc1.gream.domain.product.controller;

import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.mapper.BuyMapper;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.mapper.SellMapper;
import bc1.gream.domain.product.dto.ProductQueryResponseDto;
import bc1.gream.domain.product.dto.TradeResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.BuyOrderQueryService;
import bc1.gream.domain.product.service.SellOrderQueryService;
import bc1.gream.domain.product.service.query.ProductOrderQueryService;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.validator.OrderCriteriaValidator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final SellOrderQueryService sellOrderQueryService;
    private final BuyOrderQueryService buyOrderQueryService;

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

    @GetMapping("/{id}/trade")
    public RestResponse<List<TradeResponseDto>> findAllTrades(
        @PathVariable("id") Long productId
    ) {
        List<Order> allTrades = productOrderQueryService.findAllTradesOf(productId);
        List<TradeResponseDto> tradeResponseDtos = allTrades.stream()
            .map(OrderMapper.INSTANCE::ofTradedOrder)
            .toList();
        return RestResponse.success(tradeResponseDtos);
    }

    @GetMapping("/{id}/sell")
    public RestResponse<List<TradeResponseDto>> findAllSellBidPrices(
        @PathVariable("id") Long productId,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Sell.class, pageable);
        Page<Sell> allSellBids = sellOrderQueryService.findAllSellBidsOf(productId, pageable);
        List<TradeResponseDto> tradeResponseDtos = Optional.ofNullable(allSellBids)
            .map(sells -> sells.stream()
                .map(SellMapper.INSTANCE::toTradeResponseDto)
                .toList())
            .orElse(null);
        return RestResponse.success(tradeResponseDtos);
    }

    @GetMapping("/{id}/buy")
    public RestResponse<List<TradeResponseDto>> findAllBuyBidPrices(
        @PathVariable("id") Long productId,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Buy.class, pageable);
        Page<Buy> allBuyBids = buyOrderQueryService.findAllBuyBidsOf(productId, pageable);
        List<TradeResponseDto> tradeResponseDtos = Optional.ofNullable(allBuyBids)
            .map(buys -> buys.stream()
                .map(BuyMapper.INSTANCE::toTradeResponseDto)
                .toList())
            .orElse(null);
        return RestResponse.success(tradeResponseDtos);
    }
}