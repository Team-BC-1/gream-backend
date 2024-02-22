package bc1.gream.domain.sell.provider;

import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.gifticon.service.command.GifticonCommandService;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellCancelBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.mapper.SellMapper;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.sell.service.command.SellCommandService;
import bc1.gream.domain.sell.service.helper.deadline.DeadlineCalculator;
import bc1.gream.domain.user.entity.User;
import bc1.gream.infra.s3.S3ImageService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellBidProvider {

    private final SellRepository sellRepository;
    private final GifticonCommandService gifticonCommandService;
    private final SellCommandService sellCommandService;
    private final S3ImageService s3ImageService;

    @Transactional
    public SellBidResponseDto createSellBid(User seller, SellBidRequestDto requestDto, Product product) {
        // 기프티콘 이미지 S3 저장
        String url = s3ImageService.getUrlAfterUpload(requestDto.file());
        // 기프티콘 생성, 저장
        Gifticon gifticon = gifticonCommandService.saveGifticon(url, null);

        // 마감기한 계산
        LocalDateTime deadlineAt = DeadlineCalculator.getDeadlineOf(requestDto.period());
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

    @Transactional
    public SellCancelBidResponseDto sellCancelBid(User seller, Long sellId) {
        Sell deletedSell = sellCommandService.deleteSellByIdAndUser(sellId, seller);
        gifticonCommandService.delete(deletedSell.getGifticon());

        return new SellCancelBidResponseDto(sellId);
    }
}
