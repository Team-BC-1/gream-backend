package bc1.gream.domain.user.service.query;

import bc1.gream.domain.user.entity.Refund;
import bc1.gream.domain.user.repository.RefundRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundQueryService {

    private final RefundRepository refundRepository;

    public Refund findRefund(Long refundId) {
        return refundRepository.findById(refundId).orElseThrow(
            () -> new GlobalException(ResultCase.REFUND_NOT_FOUND)
        );
    }

}
