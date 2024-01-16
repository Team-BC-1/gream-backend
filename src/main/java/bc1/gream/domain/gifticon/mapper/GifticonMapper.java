package bc1.gream.domain.gifticon.mapper;

import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.sell.dto.response.UserSoldGifticonResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface GifticonMapper {

    GifticonMapper INSTANCE = Mappers.getMapper(GifticonMapper.class);

    @Mapping(source = "id", target = "gifticonId")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getBrand())", target = "brand")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getName())", target = "name")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getDescription())", target = "description")
    UserSoldGifticonResponseDto toUserBoughtGifticonResponseDto(Gifticon gifticon);
}
