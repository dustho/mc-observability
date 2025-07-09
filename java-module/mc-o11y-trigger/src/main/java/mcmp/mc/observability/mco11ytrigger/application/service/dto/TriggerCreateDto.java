package mcmp.mc.observability.mco11ytrigger.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerCreateRequest;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerCreateDto {

  private String title;
  private String namespaceId;
  private String targetId;
  private boolean isActive;

  public static TriggerCreateDto from(TriggerCreateRequest req) {
    TriggerCreateDto dto = new TriggerCreateDto();
    dto.title = req.title();
    dto.namespaceId = req.namespaceId();
    dto.targetId = req.targetId();
    dto.isActive = req.isActive();
    return dto;
  }
}
