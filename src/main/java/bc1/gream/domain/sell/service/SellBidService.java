package bc1.gream.domain.sell.service;

import bc1.gream.domain.gifticon.service.GifticonService;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellCancelBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.mapper.SellMapper;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.sell.service.helper.deadline.Deadline;
import bc1.gream.domain.sell.service.helper.deadline.DeadlineCalculator;
import bc1.gream.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellBidService {

    private final SellRepository sellRepository;
    private final GifticonService gifticonService;
    private final SellService sellService;

    public SellBidResponseDto createSellBid(User seller, SellBidRequestDto requestDto, Product product) {
        // 기프티콘 생성, 저장
        Gifticon gifticon = gifticonService.saveGifticon(requestDto.gifticonUrl(), null);
        // 마감기한 지정 : LocalTime.Max :: 23시 59분 59초
        Integer period = Deadline.getPeriod(requestDto.period());
        LocalDateTime deadlineAt = DeadlineCalculator.calculateDeadlineBy(LocalDate.now(), LocalTime.MAX,
            period);

        // 판매입찰 생성 및 저장
        Sell sell = Sell.builder()
            .price(requestDto.price())
            .deadlineAt(deadlineAt)
            .user(seller)
            .product(product)
            .gifticon(gifticon)
            .build();
        Sell savedSell = sellRepository.save(sell);

        // 매퍼로 변환
        return SellMapper.INSTANCE.toSellBidResponseDto(savedSell);
    }


    public SellCancelBidResponseDto sellCancelBid(User seller, Long sellId) {
        Sell deletedSell = sellService.deleteSellByIdAndUser(sellId, seller);
        gifticonService.delete(deletedSell.getGifticon());

        return new SellCancelBidResponseDto(sellId);
    }
}
