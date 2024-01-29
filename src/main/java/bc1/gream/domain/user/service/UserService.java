package bc1.gream.domain.user.service;

import bc1.gream.domain.user.dto.request.UserPointRefundRequestDto;
import bc1.gream.domain.user.dto.request.UserSignupRequestDto;
import bc1.gream.domain.user.dto.response.UserPointRefundResponseDto;
import bc1.gream.domain.user.dto.response.UserPointResponseDto;
import bc1.gream.domain.user.dto.response.UserSignupResponseDto;
import bc1.gream.domain.user.entity.Provider;
import bc1.gream.domain.user.entity.Refund;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.entity.UserRole;
import bc1.gream.domain.user.mapper.RefundMapper;
import bc1.gream.domain.user.mapper.UserMapper;
import bc1.gream.domain.user.repository.RefundRepository;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefundRepository refundRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto request) {

        validateExistingUser(request);

        User user = User.builder()
            .loginId(request.loginId())
            .nickname(request.nickname())
            .password(passwordEncoder.encode(request.password()))
            .role(UserRole.USER)
            .provider(Provider.LOCAL)
            .build();

        userRepository.save(user);

        return new UserSignupResponseDto();
    }


    public UserPointResponseDto pointsCheck(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return UserMapper.INSTANCE.toUserPointResponseDto(user);
    }

    private void validateExistingUser(UserSignupRequestDto request) {
        if (userRepository.existsByLoginId(request.loginId())) {
            throw new GlobalException(ResultCase.DUPLICATED_LOGIN_ID);
        }
        if (userRepository.existsByNickname(request.nickname())) {
            throw new GlobalException(ResultCase.DUPLICATED_NICKNAME);
        }
    }

    public User findUser(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
            () -> new GlobalException(ResultCase.USER_NOT_FOUND)
        );

        return user;
    }

    @Transactional
    public UserPointRefundResponseDto refundsPoint(User user, UserPointRefundRequestDto requestDto) {
        User findUser = findUser(user.getLoginId());

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

    public Refund findRefund(Long refundId) {
        return refundRepository.findById(refundId).orElseThrow(
            () -> new GlobalException(ResultCase.REFUND_NOT_FOUND)
        );
    }
}
