package bc1.gream.domain.sell.provider;

import bc1.gream.domain.gifticon.service.GifticonQueryService;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSoldGifticonProvider {

    private final OrderQueryService orderQueryService;
    private final GifticonQueryService gifticonQueryService;

    public List<Gifticon> getBoughtGifticonOf(User user) {
        return gifticonQueryService.findAllSoldBySeller(user);
    }
}
