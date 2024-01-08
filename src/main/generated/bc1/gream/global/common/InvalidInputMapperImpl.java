package bc1.gream.global.common;

import javax.annotation.processing.Generated;
import org.springframework.validation.FieldError;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-08T20:15:35+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class InvalidInputMapperImpl implements InvalidInputMapper {

    @Override
    public InvalidInputResponseDto toInvalidInputResponseDto(FieldError fieldError) {
        if ( fieldError == null ) {
            return null;
        }

        InvalidInputResponseDto.InvalidInputResponseDtoBuilder invalidInputResponseDto = InvalidInputResponseDto.builder();

        invalidInputResponseDto.message( fieldError.getDefaultMessage() );
        invalidInputResponseDto.field( fieldError.getField() );

        return invalidInputResponseDto.build();
    }
}
