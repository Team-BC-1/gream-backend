package bc1.gream.domain.sell.service;

import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellServiceMapper {

    SellServiceMapper INSTANCE = Mappers.getMapper(SellServiceMapper.class);

    SellNowResponseDto toSellNowResponseDto(Sell sell);
}
