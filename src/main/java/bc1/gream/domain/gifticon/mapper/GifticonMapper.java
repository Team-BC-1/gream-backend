package bc1.gream.domain.gifticon.mapper;

import bc1.gream.domain.buy.dto.response.BuyCheckOrderResponseDto;
import bc1.gream.domain.order.entity.Gifticon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface GifticonMapper {

    GifticonMapper INSTANCE = Mappers.getMapper(GifticonMapper.class);

    @Mapping(source = "id", target = "gifticonId")
    @Mapping(expression = "java(gifticon.getOrder().getId())", target = "orderId")
    @Mapping(expression = "java(gifticon.getOrder().getCreatedAt())", target = "orderCreatedAt")
    @Mapping(expression = "java(gifticon.getOrder().getExpectedPrice())", target = "expectedPrice")
    @Mapping(expression = "java(gifticon.getOrder().getFinalPrice())", target = "finalPrice")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getId())", target = "productId")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getName())", target = "productBrand")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getBrand())", target = "productName")
    BuyCheckOrderResponseDto toBuyCheckOrderResponseDto(Gifticon gifticon);
}
