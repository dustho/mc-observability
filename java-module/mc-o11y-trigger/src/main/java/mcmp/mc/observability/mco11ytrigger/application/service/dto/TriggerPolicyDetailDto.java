package mcmp.mc.observability.mco11ytrigger.application.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerPolicyDetailDto {

	private long id;
	private String title;
	private String description;
	private ThresholdCondition thresholdCondition;
	private String resourceType;
	private String aggregationType;
	private String holdDuration;
	private String repeatInterval;
	private List<TriggerTargetDetailDto> targets;
}
