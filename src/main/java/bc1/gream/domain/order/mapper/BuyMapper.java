package bc1.gream.domain.order.mapper;

import bc1.gream.domain.order.dto.response.BuyBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyNowResponseDto;
import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.dto.response.TradeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyMapper {

    BuyMapper INSTANCE = Mappers.getMapper(BuyMapper.class);

    @Mapping(source = "id", target = "buyId")
    BuyBidResponseDto toBuyBidResponseDto(Buy buy);

    @Mapping(target = "price", source = "price")
    @Mapping(target = "tradeDate", source = "createdAt")
    TradeResponseDto toTradeResponseDto(Buy buy);

    @Mapping(source = "id", target = "orderId")
    BuyNowResponseDto toBuyNowResponseDto(Order order);
}
