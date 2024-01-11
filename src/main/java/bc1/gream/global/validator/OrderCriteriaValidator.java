package bc1.gream.global.validator;

import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class OrderCriteriaValidator {

    /**
     * Entity에 대한 정렬기준치 입력값에 대한 검증
     *
     * @param target   도메인 엔티티 클래스 정보
     * @param pageable pageable 입력값
     * @throws GlobalException if Entity has no field by name of pageable sort
     * @author 임지훈
     */
    public static void validateOrderCriteria(Class<?> target, Pageable pageable) throws GlobalException {
        List<String> orderCriterias = pageable.getSort().stream()
            .map(Sort.Order::getProperty)
            .toList();
        if (orderCriterias.isEmpty()) {
            return;
        }

        List<String> fields = Arrays.stream(target.getDeclaredFields())
            .map(Field::getName)
            .toList();
        boolean hasNotOrderCriteria = orderCriterias.stream()
            .anyMatch(criteria -> !fields.contains(criteria));
        if (hasNotOrderCriteria) {
            throw new GlobalException(ResultCase.INVALID_ORDER_CRITERIA);
        }
    }
}
