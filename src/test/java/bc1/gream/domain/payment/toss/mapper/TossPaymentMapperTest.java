package bc1.gream.domain.payment.toss.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.payment.toss.dto.request.TossPaymentInitialRequestDto;
import bc1.gream.domain.payment.toss.entity.OrderName;
import bc1.gream.domain.payment.toss.entity.PayType;
import bc1.gream.domain.payment.toss.entity.TossPayment;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.UserTest;
import org.junit.jupiter.api.Test;

class TossPaymentMapperTest implements UserTest {

    @Test
    public void 사용자와토스페이결제초기요청_토프페이로_변환() {
        // GIVEN
        User user = TEST_USER;
        TossPaymentInitialRequestDto requestDto = new TossPaymentInitialRequestDto(PayType.CARD, 3000L, OrderName.CHARGE_POINT);

        // WHEN
        TossPayment payment = TossPaymentMapper.INSTANCE.fromTossPaymentInitialRequestDto(user, requestDto);

        // THEN
        assertEquals(payment.getUser(), user);
        assertEquals(payment.getPayType(), requestDto.payType());
        assertEquals(payment.getAmount(), requestDto.amount());
        assertEquals(payment.getOrderName(), requestDto.orderName());
    }
}