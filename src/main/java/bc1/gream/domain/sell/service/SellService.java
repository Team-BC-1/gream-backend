package bc1.gream.domain.sell.service;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;

    public SellBidResponseDto sellBidProduct(User user, SellBidRequestDto requestDto, Product product) {
        Long price = requestDto.price();
        LocalDate date = LocalDate.now();
        LocalDateTime deadlineAt = date.atTime(LocalTime.MAX).plusDays(7);
        if(requestDto.period() != null){
            deadlineAt = date.atTime(LocalTime.MAX).plusDays(requestDto.period());
        }

        Sell sell = Sell.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .user(user)
            .product(product)
            .build();

        Sell savedSell = sellRepository.save(sell);

        return SellServiceMapper.INSTANCE.toSellBidResponseDto(savedSell);
    }
}
