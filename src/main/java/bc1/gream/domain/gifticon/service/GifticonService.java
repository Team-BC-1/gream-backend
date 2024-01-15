package bc1.gream.domain.gifticon.service;

import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GifticonService {

    private final GifticonRepository gifticonRepository;

    @Transactional
    public Gifticon saveGifticon(String gifticonUrl, Order order) {
        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(gifticonUrl)
            .order(order)
            .build();
        return gifticonRepository.save(gifticon);
    }

    @Transactional
    public void delete(Gifticon gifticon) {
        gifticonRepository.delete(gifticon);
    }
}
