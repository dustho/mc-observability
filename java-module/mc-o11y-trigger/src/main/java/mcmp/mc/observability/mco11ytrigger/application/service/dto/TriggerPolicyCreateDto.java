package mcmp.mc.observability.mco11ytrigger.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerPolicyCreateRequest;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerPolicyCreateDto {

  private String title;
  private String description;
  private ThresholdCondition thresholdCondition;
  private ResourceType resourceType;
  private AggregationType aggregationType;
  private String holdDuration;
  private String repeatInterval;

  public static TriggerPolicyCreateDto from(TriggerPolicyCreateRequest req) {
    TriggerPolicyCreateDto dto = new TriggerPolicyCreateDto();
    dto.title = req.title();
    dto.description = req.description();
    dto.thresholdCondition = req.thresholdCondition();
    dto.resourceType = req.resourceType();
    dto.aggregationType = req.aggregationType();
    dto.holdDuration = req.holdDuration();
    dto.repeatInterval = req.repeatInterval();

    return dto;
  }
}
