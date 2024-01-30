package bc1.gream.domain.admin.service;

import bc1.gream.domain.admin.dto.request.AdminRefundPassResponseDto;
import bc1.gream.domain.user.entity.Refund;
import bc1.gream.domain.user.repository.RefundRepository;
import bc1.gream.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final RefundRepository refundRepository;

    public AdminRefundPassResponseDto approveRefund(Long refundId) {

        Refund refund = userService.findRefund(refundId);

        refundRepository.delete(refund);

        return new AdminRefundPassResponseDto();
    }
}
