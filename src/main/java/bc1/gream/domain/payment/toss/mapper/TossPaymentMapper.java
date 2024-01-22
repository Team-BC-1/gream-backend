package bc1.gream.domain.payment.toss.mapper;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentFailResponseDto;
import bc1.gream.domain.payment.toss.dto.response.TossPaymentInitialResponseDto;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TossPaymentMapper {

    TossPaymentMapper INSTANCE = Mappers.getMapper(TossPaymentMapper.class);

    // orderId 는 UUID 를 사용한 랜덤값 사용
    @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)")
    TossPayment fromTossPaymentInitialRequestDto(User user, TossPaymentInitialRequestDto requestDto);

    @Mapping(expression = "java( tossPayment.getPayType() )", target = "paymentPayType")
    @Mapping(expression = "java( tossPayment.getAmount() )", target = "paymentAmount")
    @Mapping(expression = "java( tossPayment.getOrderId() )", target = "paymentOrderId")
    @Mapping(expression = "java( tossPayment.getOrderName() )", target = "paymentOrderName")
    @Mapping(expression = "java( tossPayment.getUser().getLoginId() )", target = "userLoginId")
    @Mapping(expression = "java( tossPayment.getUser().getNickname() )", target = "userNickname")
    @Mapping(expression = "java( successUrl )", target = "paymentSuccessUrl")
    @Mapping(expression = "java( failUrl )", target = "paymentFailUrl")
    @Mapping(expression = "java( tossPayment.getCreatedAt() )", target = "paymentCreatedAt")
    @Mapping(expression = "java( Boolean.TRUE )", target = "paymentHasSuccess")
    TossPaymentInitialResponseDto toTossPaymentInitialResponseDto(
        TossPayment tossPayment,
        String successUrl,
        String failUrl
    );

    TossPaymentFailResponseDto toTossPaymentFailResponseDto(String errorCode, String errorMsg, Long orderId);
}
