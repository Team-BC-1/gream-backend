package bc1.gream.domain.sell.mapper;

import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellTradeResponseDto;
import bc1.gream.domain.sell.dto.response.UserSellBidOnProgressResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellMapper {

    SellMapper INSTANCE = Mappers.getMapper(SellMapper.class);

    @Mapping(source = "id", target = "sellId")
    @Mapping(source = "price", target = "sellPrice")
    SellBidResponseDto toSellBidResponseDto(Sell sell);

    @Mapping(source = "id", target = "sellId")
    @Mapping(source = "price", target = "sellPrice")
    @Mapping(source = "createdAt", target = "sellTradeDate")
    SellTradeResponseDto toSellTradeResponseDto(Sell sell);


    @Mapping(source = "id", target = "sellId")
    @Mapping(source = "price", target = "sellPrice")
    @Mapping(source = "createdAt", target = "sellBidStartedAt")
    @Mapping(source = "deadlineAt", target = "sellBidDeadlineAt")
    @Mapping(expression = "java(sell.getProduct().getId())", target = "productId")
    @Mapping(expression = "java(sell.getProduct().getBrand())", target = "productBrand")
    @Mapping(expression = "java(sell.getProduct().getName())", target = "productName")
    @Mapping(expression = "java(sell.getGifticon().getId())", target = "gifticonId")
    @Mapping(expression = "java(sell.getGifticon().getGifticonUrl())", target = "gifticonImageUrl")
    UserSellBidOnProgressResponseDto toUserSellOnProgressResponseDto(Sell sell);
}
