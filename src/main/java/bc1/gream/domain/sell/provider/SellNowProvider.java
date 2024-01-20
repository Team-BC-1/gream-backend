package bc1.gream.domain.sell.provider;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.service.command.BuyCommandService;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.gifticon.service.command.GifticonCommandService;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.S3ExceptionHandlingUtil;
import bc1.gream.infra.s3.S3ImageService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellNowProvider {

    private final BuyQueryService buyQueryService;
    private final BuyCommandService commandService;
    private final CouponQueryService couponQueryService;
    private final OrderCommandService orderCommandService;
    private final GifticonCommandService gifticonCommandService;
    private final S3ImageService s3ImageService;

    @Transactional
    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {

        // 해당상품과 가격에 대한 구매입찰
        Buy buy = buyQueryService.getRecentBuyBidOf(productId, requestDto.price());
        // 쿠폰 조회
        Coupon coupon = getCouponFrom(buy);
        // 새로운 주문
        Order order = saveOrder(buy, user, coupon);

        // 기프티콘 이미지 S3 저장
        String url = s3ImageService.getUrlAfterUpload(requestDto.file());
        // 새로운 기프티콘 저장
        gifticonCommandService.saveGifticon(url, order);

        // 판매에 따른 사용자 포인트 충전
        S3ExceptionHandlingUtil.tryWithS3Cleanup(
            () -> user.increasePoint(order.getExpectedPrice()),
            s3ImageService,
            url,
            ResultCase.USER_ADD_POINT_FAIL);
        // 구매입찰 삭제
        S3ExceptionHandlingUtil.tryWithS3Cleanup(
            () -> user.increasePoint(order.getExpectedPrice()),
            s3ImageService,
            url,
            ResultCase.SELL_BID_DELETE_FAIL);

        // 매퍼를 통해 변환
        return OrderMapper.INSTANCE.toSellNowResponseDto(order);
    }

    /**
     * 구매입찰로부터 쿠폰 조회
     *
     * @param buy 구매입찰
     * @return 구매입찰 시 등록된 쿠폰, 없다면 null 반환
     */
    private Coupon getCouponFrom(Buy buy) {
        if (Objects.isNull(buy.getCouponId())) {
            return null;
        }
        // 쿠폰 조회, 사용처리
        Coupon coupon = couponQueryService.findCouponById(buy.getCouponId(), buy.getUser());
        coupon.changeStatus(CouponStatus.ALREADY_USED);
        return coupon;
    }

    private Order saveOrder(Buy buy, User seller, Coupon coupon) {
        if (coupon != null) {
            return orderCommandService.saveOrderOfBuy(buy, seller, coupon);
        }
        return orderCommandService.saveOrderOfBuyNotCoupon(buy, seller);
    }
}
