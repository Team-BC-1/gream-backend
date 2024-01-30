package bc1.gream.domain.coupon.mapper;

import bc1.gream.domain.admin.dto.response.AdminCreateCouponResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponAvailableResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUnavailableResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CouponMapper {

    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    @Mapping(source = "id", target = "couponId")
    @Mapping(source = "name", target = "couponName")
    @Mapping(source = "discountType", target = "couponDiscountType")
    @Mapping(source = "discount", target = "couponDiscount")
    CouponAvailableResponseDto toCouponListResponseDto(Coupon coupon);


    @Mapping(source = "id", target = "couponId")
    @Mapping(source = "name", target = "couponName")
    @Mapping(source = "discountType", target = "couponDiscountType")
    @Mapping(source = "discount", target = "couponDiscount")
    CouponUnavailableResponseDto toCouponUsedListResponseDto(Coupon coupon);

    AdminCreateCouponResponseDto toAdminCreateCouponResponseDto(Coupon coupon);
}
