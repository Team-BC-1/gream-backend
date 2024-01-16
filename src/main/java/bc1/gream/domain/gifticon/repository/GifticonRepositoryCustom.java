package bc1.gream.domain.gifticon.repository;

import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.user.entity.User;
import java.util.List;

public interface GifticonRepositoryCustom {

    List<Gifticon> findAllSoldBySeller(User user);
}
