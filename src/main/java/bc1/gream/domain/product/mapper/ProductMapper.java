package bc1.gream.domain.product.mapper;

import bc1.gream.domain.product.dto.response.ProductDetailsResponseDto;
import bc1.gream.domain.product.dto.response.ProductDislikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikeResponseDto;
import bc1.gream.domain.product.dto.response.ProductLikesResponseDto;
import bc1.gream.domain.product.dto.response.ProductPreviewByNameResponseDto;
import bc1.gream.domain.product.dto.response.ProductPreviewResponseDto;
import bc1.gream.domain.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "brand", target = "productBrand")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "imageUrl", target = "productImageUrl")
    @Mapping(source = "description", target = "productDescription")
    @Mapping(source = "price", target = "productPrice")
    ProductDetailsResponseDto toDetailsResponseDto(Product product);

    ProductLikeResponseDto toLikeResponseDto(String message);

    ProductDislikeResponseDto toDislikeResponseDto(String message);


    @Mapping(source = "id", target = "productId")
    @Mapping(source = "brand", target = "productBrand")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "imageUrl", target = "productImageUrl")
    @Mapping(source = "description", target = "productDescription")
    @Mapping(source = "price", target = "productPrice")
    ProductPreviewResponseDto toPreviewResponseDto(Product product);


    @Mapping(source = "id", target = "productId")
    @Mapping(source = "brand", target = "productBrand")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "imageUrl", target = "productImageUrl")
    @Mapping(source = "description", target = "productDescription")
    @Mapping(source = "price", target = "productPrice")
    ProductLikesResponseDto toProductLikesResponseDto(Product product);


    @Mapping(source = "id", target = "productId")
    @Mapping(source = "brand", target = "productBrand")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "imageUrl", target = "productImageUrl")
    @Mapping(source = "description", target = "productDescription")
    @Mapping(source = "price", target = "productPrice")
    ProductPreviewByNameResponseDto toProductPreviewByNameResponseDto(Product product);
}
