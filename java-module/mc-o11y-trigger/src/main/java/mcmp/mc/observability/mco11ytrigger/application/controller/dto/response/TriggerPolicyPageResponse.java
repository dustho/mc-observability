package mcmp.mc.observability.mco11ytrigger.application.controller.dto.response;

import java.util.List;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.CustomPageDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyDetailDto;
import org.springframework.data.domain.Pageable;

public record TriggerPolicyPageResponse(
  List<TriggerPolicyDetailDto> content,
  Pageable pageable,
  long totalPages,
  long totalElements,
  long numberOfElements
) {
  public static TriggerPolicyPageResponse from(CustomPageDto<TriggerPolicyDetailDto> dto) {
    return new TriggerPolicyPageResponse(
        dto.getContent(),
        dto.getPageable(),
        dto.getTotalPages(),
        dto.getTotalElements(),
        dto.getNumberOfElements()
    );
  }
}
