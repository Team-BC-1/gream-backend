package bc1.gream.domain.order.mapper;

import bc1.gream.domain.order.dto.response.SellBidResponseDto;
import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.product.dto.response.TradeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellMapper {

    SellMapper INSTANCE = Mappers.getMapper(SellMapper.class);

    @Mapping(source = "id", target = "sellId")
    SellBidResponseDto toSellBidResponseDto(Sell sell);

    @Mapping(target = "price", source = "price")
    @Mapping(target = "tradeDate", source = "createdAt")
    TradeResponseDto toTradeResponseDto(Sell sell);
}
