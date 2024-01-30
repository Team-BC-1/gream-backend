package bc1.gream.domain.gifticon.service.command;

import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GifticonCommandService {

    private final GifticonRepository gifticonRepository;

    @Transactional
    public Gifticon saveGifticon(String gifticonUrl) {
        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(gifticonUrl)
            .build();
        return gifticonRepository.save(gifticon);
    }

    @Transactional
    public void delete(Gifticon gifticon) {
        gifticonRepository.delete(gifticon);
    }
}
