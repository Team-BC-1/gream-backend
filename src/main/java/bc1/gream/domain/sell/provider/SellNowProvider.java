package bc1.gream.domain.sell.provider;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.service.command.BuyCommandService;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.gifticon.service.command.GifticonCommandService;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.user.entity.User;
import bc1.gream.infra.s3.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellNowProvider {

    private final BuyQueryService buyQueryService;
    private final BuyCommandService buyCommandService;
    private final CouponQueryService couponQueryService;
    private final OrderCommandService orderCommandService;
    private final GifticonCommandService gifticonCommandService;
    private final S3ImageService s3ImageService;

    @Transactional
    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {

        // 해당상품과 가격에 대한 구매입찰
        Buy buy = buyQueryService.getRecentBuyBidOf(productId, requestDto.price());
        // 쿠폰 조회
        Coupon coupon = couponQueryService.getCouponFrom(buy);
        // 새로운 주문
        Order order = orderCommandService.saveOrder(buy, user, coupon);

        // 기프티콘 이미지 S3 저장
        String url = s3ImageService.getUrlAfterUpload(requestDto.file());

        // 새로운 기프티콘 저장
        gifticonCommandService.saveGifticon(url, order);

        // 판매에 따른 사용자 포인트 충전
        user.increasePoint(order.getExpectedPrice());

        // 구매입찰 삭제
        buyCommandService.delete(buy);

        // 매퍼를 통해 변환
        return OrderMapper.INSTANCE.toSellNowResponseDto(order);
    }
}
