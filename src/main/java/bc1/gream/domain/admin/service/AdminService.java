package bc1.gream.domain.admin.service;

import bc1.gream.domain.admin.dto.request.AdminRefundPassResponseDto;
import bc1.gream.domain.user.entity.Refund;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.entity.UserRole;
import bc1.gream.domain.user.repository.RefundRepository;
import bc1.gream.domain.user.service.UserService;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final RefundRepository refundRepository;

    public AdminRefundPassResponseDto refundPass(User user, Long refundId) {

        User AdminUser = checkUser(user.getLoginId());

        Refund refund = userService.findRefund(refundId);

        refundRepository.delete(refund);

        return new AdminRefundPassResponseDto();
    }

    private User checkUser(String loginId) {
        User user = userService.findUser(loginId);

        if (isAdmin(user)) {
            return user;
        }

        throw new GlobalException(ResultCase.NOT_AUTHORIZED);
    }

    private boolean isAdmin(User user) {
        return user.getRole().equals(UserRole.ADMIN);
    }
}
