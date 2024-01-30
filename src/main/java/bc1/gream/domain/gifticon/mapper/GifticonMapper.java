package bc1.gream.domain.gifticon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface GifticonMapper {

    GifticonMapper INSTANCE = Mappers.getMapper(GifticonMapper.class);

}
