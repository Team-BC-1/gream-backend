package bc1.gream.domain.product.controller;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.mapper.BuyMapper;
import bc1.gream.domain.common.facade.BuyOrderQueryFacade;
import bc1.gream.domain.common.facade.ProductOrderQueryFacade;
import bc1.gream.domain.common.facade.SellOrderQueryFacade;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.product.dto.response.ProductQueryResponseDto;
import bc1.gream.domain.product.dto.response.TradeResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.mapper.ProductMapper;
import bc1.gream.domain.product.service.query.ProductService;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.mapper.SellMapper;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.validator.OrderCriteriaValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 읽기 전용
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductService productService;
    private final ProductOrderQueryFacade productOrderQueryFacade;
    private final SellOrderQueryFacade sellOrderQueryFacade;
    private final BuyOrderQueryFacade buyOrderQueryFacade;

    /**
     * 상품 전체 조회
     **/
    @GetMapping
    public RestResponse<List<ProductQueryResponseDto>> findAll() {
        List<Product> products = productService.findAll();
        List<ProductQueryResponseDto> responseDtos = products.stream()
            .map(ProductMapper.INSTANCE::toQueryResponseDto)
            .toList();
        return RestResponse.success(responseDtos);
    }

    /**
     * 상품 상세조회
     **/
    @GetMapping("/{id}")
    public RestResponse<ProductQueryResponseDto> findAllBy(
        @PathVariable("id") Long productId
    ) {
        Product product = productService.findBy(productId);
        ProductQueryResponseDto responseDto = ProductMapper.INSTANCE.toQueryResponseDto(product);
        return RestResponse.success(responseDto);
    }

    /**
     * 상품 체결내역 조회
     **/
    @GetMapping("/{id}/trade")
    public RestResponse<List<TradeResponseDto>> findAllTrades(
        @PathVariable("id") Long productId
    ) {
        List<Order> allTrades = productOrderQueryFacade.findAllTradesOf(productId);
        List<TradeResponseDto> tradeResponseDtos = allTrades.stream()
            .map(OrderMapper.INSTANCE::ofTradedOrder)
            .toList();
        return RestResponse.success(tradeResponseDtos);
    }

    /**
     * 판매 입찰가 조회
     **/
    @GetMapping("/{id}/sell")
    public RestResponse<List<TradeResponseDto>> findAllSellBidPrices(
        @PathVariable("id") Long productId,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Sell.class, pageable);
        List<Sell> allSellBids = sellOrderQueryFacade.findAllSellBidsOf(productId, pageable);
        List<TradeResponseDto> tradeResponseDtos = allSellBids.stream()
            .map(SellMapper.INSTANCE::toTradeResponseDto)
            .toList();
        return RestResponse.success(tradeResponseDtos);
    }

    /**
     * 구매 입찰가 조회
     **/
    @GetMapping("/{id}/buy")
    public RestResponse<List<TradeResponseDto>> findAllBuyBidPrices(
        @PathVariable("id") Long productId,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        OrderCriteriaValidator.validateOrderCriteria(Buy.class, pageable);
        List<Buy> allBuyBids = buyOrderQueryFacade.findAllBuyBidsOf(productId, pageable);
        List<TradeResponseDto> tradeResponseDtos = allBuyBids.stream()
            .map(BuyMapper.INSTANCE::toTradeResponseDto)
            .toList();
        return RestResponse.success(tradeResponseDtos);
    }
}