package bc1.gream.global.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class OrderCriteriaValidatorTest {

    @Test
    @DisplayName("도메인 엔티티 정렬 기준치 입력값 검증 시, 올바른 정렬 기준치에 대해 통과합니다.")
    public void 도메인_엔티티_정렬기준치입력값_검증_해피케이스() {
        // GIVEN
        Pageable validRequest = PageRequest.of(0, 10, Sort.by("id").ascending());
        Pageable validRequestNonSort = PageRequest.of(0, 10);

        // WHEN
        // THEN
        assertDoesNotThrow(() -> OrderCriteriaValidator.validateOrderCriteria(Product.class, validRequest));
        assertDoesNotThrow(() -> OrderCriteriaValidator.validateOrderCriteria(Product.class, validRequestNonSort));
    }

    @Test
    @DisplayName("도메인 엔티티 정렬 기준치 입력값 검증 시, 올바르지 않은 정렬 기준치에 대해 예외처리합니다.")
    public void 도메인_엔티티_정렬기준치입력값_검증_언해피케이스() {
        // GIVEN
        Pageable invalidRequest = PageRequest.of(0, 10, Sort.by("나는바보입니다.").ascending());

        // WHEN
        // THEN
        GlobalException globalException = assertThrows(GlobalException.class,
            () -> OrderCriteriaValidator.validateOrderCriteria(Product.class, invalidRequest));
        assertEquals(ResultCase.INVALID_ORDER_CRITERIA, globalException.getResultCase());
    }
}