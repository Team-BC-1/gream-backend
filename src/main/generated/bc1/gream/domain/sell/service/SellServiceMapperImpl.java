package bc1.gream.domain.sell.service;

import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-09T00:13:44+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class SellServiceMapperImpl implements SellServiceMapper {

    @Override
    public SellBidResponseDto toSellBidResponseDto(Sell sell) {
        if ( sell == null ) {
            return null;
        }

        Long sellId = null;
        Long price = null;

        sellId = sell.getId();
        price = sell.getPrice();

        SellBidResponseDto sellBidResponseDto = new SellBidResponseDto( price, sellId );

        return sellBidResponseDto;
    }
}
