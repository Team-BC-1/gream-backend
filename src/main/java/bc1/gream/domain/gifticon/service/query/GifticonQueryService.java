package bc1.gream.domain.gifticon.service.query;


import bc1.gream.domain.buy.dto.response.BuyCheckOrderResponseDto;
import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.gifticon.mapper.GifticonMapper;
import bc1.gream.domain.gifticon.repository.GifticonRepository;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GifticonQueryService {

    private final GifticonRepository gifticonRepository;

    public List<Gifticon> findAllSoldBySeller(User user) {
        return gifticonRepository.findAllSoldBySeller(user);
    }

    public List<BuyCheckOrderResponseDto> getBoughtOrder(User user) {
        List<Gifticon> gifticons = gifticonRepository.findAllBoughtByBuyer(user);

        return gifticons.stream().map(GifticonMapper.INSTANCE::toBuyCheckOrderResponseDto).toList();
    }
}
