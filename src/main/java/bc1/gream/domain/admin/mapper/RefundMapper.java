package bc1.gream.domain.admin.mapper;

import bc1.gream.domain.admin.dto.response.AdminGetRefundResponseDto;
import bc1.gream.domain.user.entity.Refund;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefundMapper {

    RefundMapper INSTANCE = Mappers.getMapper(RefundMapper.class);

    @Mapping(source = "id", target = "refundId")
    @Mapping(expression = "java(refund.getUser().getId())", target = "userId")
    @Mapping(source = "point", target = "refundPoint")
    @Mapping(source = "bank", target = "refundBank")
    @Mapping(source = "accountNumber", target = "refundAccountNumber")
    AdminGetRefundResponseDto toAdminGetRefundResponseDto(Refund refund);
}
