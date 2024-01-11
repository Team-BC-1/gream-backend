package bc1.gream.domain.order.mapper;

import bc1.gream.domain.order.dto.response.BuyNowResponseDto;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.dto.response.TradeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "price", source = "finalPrice")
    @Mapping(target = "tradeDate", source = "createdAt")
    TradeResponseDto ofTradedOrder(Order order);

    @Mapping(source = "id", target = "orderId")
    BuyNowResponseDto toBuyNowResponseDto(Order order);
}
