package bc1.gream.domain.sell.mapper;

import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellTradeResponseDto;
import bc1.gream.domain.sell.dto.response.UserSellOnProgressResponseDto;
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


    @Mapping(source = "id", target = "sellId")
    @Mapping(source = "price", target = "sellPrice")
    @Mapping(source = "createdAt", target = "bidStartedAt")
    @Mapping(source = "deadlineAt", target = "bidDeadlineAt")
    @Mapping(expression = "java(sell.getProduct().getId())", target = "productId")
    @Mapping(expression = "java(sell.getProduct().getBrand())", target = "productBrand")
    @Mapping(expression = "java(sell.getProduct().getName())", target = "productName")
    @Mapping(expression = "java(sell.getGifticon().getId())", target = "gifticonId")
    @Mapping(expression = "java(sell.getGifticon().getGifticonUrl())", target = "gifticonImageUrl")
    UserSellOnProgressResponseDto toUserSellOnProgressResponseDto(Sell sell);
}
