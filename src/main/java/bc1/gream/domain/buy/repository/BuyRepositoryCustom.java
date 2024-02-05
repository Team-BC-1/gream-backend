package bc1.gream.domain.buy.repository;

import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuyRepositoryCustom {

    Page<Buy> findAllPricesOf(Product product, Pageable pageable);

    Optional<Buy> findByProductIdAndPrice(Long productId, Long price, LocalDateTime dateTime);

    Page<BuyPriceToQuantityResponseDto> findAllPriceToQuantityOf(Product product, Pageable pageable, LocalDateTime dateTime);

    void deleteBuysOfDeadlineBefore(LocalDateTime now);

    List<BuyCheckBidResponseDto> findAllBuyBidCoupon(User user, LocalDateTime dateTime);
}
