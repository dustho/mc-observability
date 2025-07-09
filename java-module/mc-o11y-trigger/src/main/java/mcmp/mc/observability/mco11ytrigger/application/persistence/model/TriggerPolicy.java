package mcmp.mc.observability.mco11ytrigger.application.persistence.model;

import jakarta.persistence.*;

import lombok.Getter;

import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;

@Getter
@Table(name = "trigger_policy")
@Entity
public class TriggerPolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private String thresholdCondition;

	private String resourceType;

	private String aggregationType;

	private String holdDuration;

	private String repeatInterval;

	private int version;

	public static TriggerPolicy create(TriggerPolicyCreateDto dto) {
		TriggerPolicy entity = new TriggerPolicy();
		entity.title = dto.getTitle();
		entity.description = dto.getDescription();
		entity.thresholdCondition = dto.getThresholdCondition().toJson();
		entity.resourceType = dto.getResourceType().getMeasurement();
		entity.aggregationType = dto.getAggregationType().getName();
		entity.holdDuration = dto.getHoldDuration();
		entity.repeatInterval = dto.getRepeatInterval();
		entity.version = 1;
		return entity;
	}

	public static TriggerPolicy create(String title, String description, String thresholdCondition, String resourceType,
			String aggregationType, String holdDuration, String repeatInterval) {
		TriggerPolicy entity = new TriggerPolicy();
		entity.title = title;
		entity.description = description;
		entity.thresholdCondition = thresholdCondition;
		entity.resourceType = resourceType;
		entity.aggregationType = aggregationType;
		entity.holdDuration = holdDuration;
		entity.repeatInterval = repeatInterval;
		return entity;
	}
}
