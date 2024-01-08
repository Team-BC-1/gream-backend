package bc1.gream.domain.buy.service;

import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyServiceMapper {

    BuyServiceMapper INSTANCE = Mappers.getMapper(BuyServiceMapper.class);

    @Mapping(source = "id", target = "buyId")
    BuyBidResponseDto toBuyBidResponseDto(Buy buy);
}
