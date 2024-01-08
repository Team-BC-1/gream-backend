package bc1.gream.global.common;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@JsonSerialize
@Builder // 테스트 용
public record InvalidInputResponseDto(String field, String message) {

}
