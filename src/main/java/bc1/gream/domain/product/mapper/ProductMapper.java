package bc1.gream.domain.product.mapper;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.dto.ProductLikeResponseDto;
import bc1.gream.domain.product.dto.ProductQueryResponseDto;
import bc1.gream.domain.product.dto.TradeResponseDto;
import bc1.gream.domain.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductQueryResponseDto toQueryResponseDto(Product product);

    ProductLikeResponseDto toLikeResponseDto(String message);

    TradeResponseDto toTradeResponseDto(Order order);
}
