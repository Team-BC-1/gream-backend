package bc1.gream.domain.user.mapper;

import bc1.gream.domain.user.dto.response.UserPointRefundResponseDto;
import bc1.gream.domain.user.entity.Refund;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefundMapper {

    RefundMapper INSTANCE = Mappers.getMapper(RefundMapper.class);

    UserPointRefundResponseDto toUserPointRefundResponseDto(Refund refund);

}
