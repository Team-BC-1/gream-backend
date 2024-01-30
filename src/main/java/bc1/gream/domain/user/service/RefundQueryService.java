package bc1.gream.domain.user.service;

import bc1.gream.domain.user.entity.Refund;
import bc1.gream.domain.user.repository.RefundRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefundQueryService {

    private final RefundRepository refundRepository;

    /**
     * 신청된 모든 환급 리스트를 반환
     */
    public List<Refund> getRefunds() {
        return refundRepository.findAll();
    }
}
