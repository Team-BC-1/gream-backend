package bc1.gream.domain.user.service.command;

import bc1.gream.domain.admin.dto.request.AdminRefundPassResponseDto;
import bc1.gream.domain.user.dto.request.UserPointRefundRequestDto;
import bc1.gream.domain.user.dto.response.UserPointRefundResponseDto;
import bc1.gream.domain.user.entity.Refund;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.mapper.RefundMapper;
import bc1.gream.domain.user.repository.RefundRepository;
import bc1.gream.domain.user.service.UserService;
import bc1.gream.domain.user.service.query.RefundQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundCommandService {

    private final RefundRepository refundRepository;
    private final RefundQueryService refundQueryService;
    private final UserService userService;

    @Transactional
    public UserPointRefundResponseDto refundsPoint(User user, UserPointRefundRequestDto requestDto) {
        User findUser = userService.findUser(user.getLoginId());

        Refund refund = Refund.builder()
            .point(requestDto.point())
            .bank(requestDto.bank())
            .accountNumber(requestDto.accountNumber())
            .user(findUser)
            .build();

        findUser.decreasePoint(requestDto.point());

        Refund savedRefund = refundRepository.save(refund);

        return RefundMapper.INSTANCE.toUserPointRefundResponseDto(savedRefund, findUser);
    }

    @Transactional
    public AdminRefundPassResponseDto approveRefund(Long id) {
        Refund refund = refundQueryService.findRefund(id);

        refundRepository.delete(refund);

        return new AdminRefundPassResponseDto();
    }
}
