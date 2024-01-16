package bc1.gream.domain.sell.provider;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSoldGifticonProvider {

    private final OrderQueryService gifticonQueryService;

    public List<Order> getBoughtGifticonOf(User user) {
        return gifticonQueryService.findAllOrderBySeller(user);
    }
}
