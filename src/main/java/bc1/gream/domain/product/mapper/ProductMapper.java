package bc1.gream.domain.product.mapper;

import bc1.gream.domain.product.dto.response.ProductDetailsResponseDto;
import bc1.gream.domain.product.dto.response.ProductDislikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikesResponseDto;
import bc1.gream.domain.product.dto.response.ProductPreviewResponseDto;
import bc1.gream.domain.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDetailsResponseDto toDetailsResponseDto(Product product);

    ProductLikeResponseDto toLikeResponseDto(String message);

    ProductDislikeResponseDto toDislikeResponseDto(String message);

    ProductPreviewResponseDto toPreviewResponseDto(Product product);

    ProductLikesResponseDto toProductLikesResponseDto(Product product);
}
