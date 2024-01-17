package bc1.gream.domain.buy.mapper;

import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.dto.response.BuyTradeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyMapper {

    BuyMapper INSTANCE = Mappers.getMapper(BuyMapper.class);

    @Mapping(source = "id", target = "buyId")
    BuyBidResponseDto toBuyBidResponseDto(Buy buy);

    @Mapping(source = "id", target = "buyId")
    @Mapping(source = "price", target = "buyPrice")
    @Mapping(source = "createdAt", target = "tradeDate")
    BuyTradeResponseDto toBuyTradeResponseDto(Buy buy);

    @Mapping(source = "discountPrice", target = "discountPrice")
    BuyCheckBidResponseDto toBuyCheckBidResponseDto(BuyCheckBidResponseDto bid, Long discountPrice);
}
