package bc1.gream.domain.payment.toss.mapper;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentFailResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.user.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TossPaymentMapper {

    TossPaymentMapper INSTANCE = Mappers.getMapper(TossPaymentMapper.class);

    // orderId 는 UUID 를 사용한 랜덤값 사용
    @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)")
    TossPayment fromTossPaymentInitialRequestDto(User user, TossPaymentInitialRequestDto requestDto);

    @Mapping(source = "payType", target = "paymentPayType")
    @Mapping(source = "amount", target = "paymentAmount")
    @Mapping(source = "orderId", target = "paymentOrderId")
    @Mapping(source = "orderName", target = "paymentOrderName")
    @Mapping(source = "user.loginId", target = "userLoginId")
    @Mapping(source = "user.nickname", target = "userNickname")
    @Mapping(expression = "java( successUrl )", target = "paymentSuccessUrl")
    @Mapping(expression = "java( failUrl )", target = "paymentFailUrl")
    @Mapping(source = "createdAt", target = "paymentCreatedAt")
    @Mapping(expression = "java( Boolean.FALSE )", target = "paymentHasSuccess")
    TossPaymentInitialResponseDto fromTossPaymentInitialResponseDto(
        TossPayment tossPayment,
        @Context String successUrl,
        @Context String failUrl
    );

    TossPaymentFailResponseDto toTossPaymentFailResponseDto(String errorCode, String errorMsg, Long orderId);
}
