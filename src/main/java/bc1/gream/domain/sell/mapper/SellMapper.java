package bc1.gream.domain.sell.mapper;

import bc1.gream.domain.product.controller.SellTradeResponseDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellMapper {

    SellMapper INSTANCE = Mappers.getMapper(SellMapper.class);

    @Mapping(source = "id", target = "sellId")
    SellBidResponseDto toSellBidResponseDto(Sell sell);

    @Mapping(source = "id", target = "sellId")
    @Mapping(source = "price", target = "sellPrice")
    @Mapping(source = "createdAt", target = "tradeDate")
    SellTradeResponseDto toSellTradeResponseDto(Sell sell);
}
