package bc1.gream.domain.product.mapper;

import bc1.gream.domain.product.dto.ProductLikeResponseDto;
import bc1.gream.domain.product.dto.ProductQueryResponseDto;
import bc1.gream.domain.product.entity.Product;
import java.math.BigInteger;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-08T20:15:35+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductQueryResponseDto toQueryResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        String brand = null;
        String name = null;
        String imageUrl = null;
        String description = null;
        BigInteger price = null;

        brand = product.getBrand();
        name = product.getName();
        imageUrl = product.getImageUrl();
        description = product.getDescription();
        if ( product.getPrice() != null ) {
            price = BigInteger.valueOf( product.getPrice() );
        }

        ProductQueryResponseDto productQueryResponseDto = new ProductQueryResponseDto( brand, name, imageUrl, description, price );

        return productQueryResponseDto;
    }

    @Override
    public ProductLikeResponseDto toLikeResponseDto(String message) {
        if ( message == null ) {
            return null;
        }

        String message1 = null;

        message1 = message;

        ProductLikeResponseDto productLikeResponseDto = new ProductLikeResponseDto( message1 );

        return productLikeResponseDto;
    }
}
