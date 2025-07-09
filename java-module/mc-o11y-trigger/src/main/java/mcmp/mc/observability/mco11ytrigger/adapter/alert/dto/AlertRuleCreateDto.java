package mcmp.mc.observability.mco11ytrigger.adapter.alert.dto;

import lombok.Getter;

import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerTarget;

@Getter
public class AlertRuleCreateDto {

	private String uuid;
	private String measurement;
	private String aggregation;
	private String field;
	private String targetId;
	private String namespaceId;
	private String title;
	private String holdDuration;
	private String thresholdExpression;

	public static AlertRuleCreateDto from(TriggerPolicy triggerPolicy, TriggerTarget triggerTarget) {
		AlertRuleCreateDto dto = new AlertRuleCreateDto();
		dto.uuid = triggerTarget.getUuid();
		dto.namespaceId = triggerTarget.getNamespaceId();
		dto.targetId = triggerTarget.getTargetId();
		dto.title = triggerPolicy.getTitle();
		dto.measurement = "cmp/autogen";
		dto.aggregation = triggerPolicy.getAggregationType();
		dto.field = triggerPolicy.getResourceType();
		dto.holdDuration = triggerPolicy.getHoldDuration();
		dto.thresholdExpression = triggerPolicy.getThresholdCondition();
		return dto;
	}
}
