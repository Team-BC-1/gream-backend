package bc1.gream.domain.order.mapper;

import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.dto.response.OrderTradeResponseDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.dto.response.UserSalesHistroyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "finalPrice", target = "orderFinalPrice")
    @Mapping(source = "createdAt", target = "orderTradeDate")
    OrderTradeResponseDto toOrderTradeResponseDto(Order order);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "expectedPrice", target = "orderExpectedPrice")
    @Mapping(source = "finalPrice", target = "orderFinalPrice")
    BuyNowResponseDto toBuyNowResponseDto(Order order);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "finalPrice", target = "orderFinalPrice")
    SellNowResponseDto toSellNowResponseDto(Order order);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "createdAt", target = "orderTradedDate")
    @Mapping(expression = "java(order.getProduct().getId())", target = "productId")
    @Mapping(expression = "java(order.getProduct().getBrand())", target = "productBrand")
    @Mapping(expression = "java(order.getProduct().getName())", target = "productName")
    @Mapping(expression = "java(order.getProduct().getImageUrl())", target = "productIamgeUrl")
    UserSalesHistroyResponseDto toOrderAsSellerResponseDto(Order order);
}
