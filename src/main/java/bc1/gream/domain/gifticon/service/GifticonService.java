package bc1.gream.domain.gifticon.service;

import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GifticonService {

    private final GifticonRepository gifticonRepository;

    public Gifticon saveGifticon(String gifticonUrl, Order order) {
        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(gifticonUrl)
            .order(order)
            .build();
        return gifticonRepository.save(gifticon);
    }

    public void delete(Gifticon gifticon) {
        gifticonRepository.delete(gifticon);
    }
}
