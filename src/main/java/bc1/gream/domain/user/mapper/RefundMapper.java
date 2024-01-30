package bc1.gream.domain.user.mapper;

import bc1.gream.domain.user.dto.response.UserPointRefundResponseDto;
import bc1.gream.domain.user.entity.Refund;
import bc1.gream.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefundMapper {

    RefundMapper INSTANCE = Mappers.getMapper(RefundMapper.class);

    @Mapping(expression = "java(refund.getPoint())", target = "refundPoint")
    @Mapping(expression = "java(user.getPoint())", target = "userPoint")
    UserPointRefundResponseDto toUserPointRefundResponseDto(Refund refund, User user);

}
