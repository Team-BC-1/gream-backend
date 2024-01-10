package bc1.gream.domain.buy.mapper;

import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.dto.TradeResponseDto;
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
}
