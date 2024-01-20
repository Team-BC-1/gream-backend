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
import bc1.gream.domain.sell.service.SellService;
import bc1.gream.domain.sell.service.helper.deadline.Deadline;
import bc1.gream.domain.sell.service.helper.deadline.DeadlineCalculator;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.S3ExceptionHandlingUtil;
import bc1.gream.infra.s3.S3ImageService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final SellService sellService;
    private final S3ImageService s3ImageService;

    @Transactional
    public SellBidResponseDto createSellBid(User seller, SellBidRequestDto requestDto, Product product) {

        // 기프티콘 이미지 S3 저장
        String url = s3ImageService.getUrlAfterUpload(requestDto.file());
        // 기프티콘 생성, 저장
        Gifticon gifticon = S3ExceptionHandlingUtil.tryWithS3Cleanup(
            () -> gifticonCommandService.saveGifticon(url, null),
            s3ImageService,
            url,
            ResultCase.GIFTICON_SAVE_FAIL);
        // 판매입찰 생성 및 저장
        Sell savedSell = S3ExceptionHandlingUtil.tryWithS3Cleanup(
            () -> saveSell(seller, requestDto, product, gifticon),
            s3ImageService,
            url,
            ResultCase.SELL_BID_SAVE_FAIL);
        // 매퍼로 변환
        return SellMapper.INSTANCE.toSellBidResponseDto(savedSell);
    }

    @Transactional
    Sell saveSell(User seller, SellBidRequestDto requestDto, Product product, Gifticon gifticon) {
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
        return sellRepository.save(sell);
    }

    public SellCancelBidResponseDto sellCancelBid(User seller, Long sellId) {
        Sell deletedSell = sellService.deleteSellByIdAndUser(sellId, seller);
        gifticonCommandService.delete(deletedSell.getGifticon());

        return new SellCancelBidResponseDto(sellId);
    }
}
