package mcmp.mc.observability.mco11ytrigger.adapter.alert.dto;

import lombok.Getter;

import mcmp.mc.observability.mco11ytrigger.application.persistence.model.Trigger;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;

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

	public static AlertRuleCreateDto from(Trigger trigger, TriggerPolicy triggerPolicy) {
		AlertRuleCreateDto dto = new AlertRuleCreateDto();
		dto.uuid = trigger.getUuid();
		dto.title = trigger.getTitle();
		dto.namespaceId = trigger.getNamespaceId();
		dto.targetId = trigger.getTargetId();
		dto.measurement = "cmp/autogen";
		dto.aggregation = triggerPolicy.getAggregationType();
		dto.field = triggerPolicy.getResourceType();
		dto.holdDuration = triggerPolicy.getHoldDuration();
		dto.thresholdExpression = triggerPolicy.getThresholdCondition();
		return dto;
	}
}
