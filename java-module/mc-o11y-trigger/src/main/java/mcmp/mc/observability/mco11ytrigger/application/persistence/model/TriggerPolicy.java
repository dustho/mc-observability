package mcmp.mc.observability.mco11ytrigger.application.persistence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;

import lombok.Getter;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyDetailDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerTargetDetailDto;

@Getter
@Table(name = "trigger_policy")
@Entity
public class TriggerPolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;

	private String description;

	private String thresholdCondition;

	private String resourceType;

	private String aggregationType;

	private String holdDuration;

	private String repeatInterval;

	@OneToMany(mappedBy = "triggerPolicy", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<TriggerTarget> triggerTargets = new ArrayList<>();

	public static TriggerPolicy create(TriggerPolicyCreateDto dto) {
		TriggerPolicy entity = new TriggerPolicy();
		entity.title = dto.getTitle();
		entity.description = dto.getDescription();
		entity.thresholdCondition = dto.getThresholdCondition().toJson();
		entity.resourceType = dto.getResourceType().getMeasurement();
		entity.aggregationType = dto.getAggregationType().getName();
		entity.holdDuration = dto.getHoldDuration();
		entity.repeatInterval = dto.getRepeatInterval();
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

	public TriggerPolicyDetailDto toDto() {
		List<TriggerTargetDetailDto> targets = triggerTargets.stream().map(TriggerTarget::toDto).toList();
		return TriggerPolicyDetailDto.builder().id(id).title(title).description(description)
				.thresholdCondition(ThresholdCondition.from(thresholdCondition)).resourceType(resourceType)
				.aggregationType(aggregationType).holdDuration(holdDuration).repeatInterval(repeatInterval)
				.targets(targets).build();
	}

	public TriggerTarget addTriggerTarget(TriggerTarget triggerTarget) {
		triggerTarget.setTriggerPolicy(this);
		triggerTargets.add(triggerTarget);
		return triggerTarget;
	}

	public void removeIfNotContains(Set<String> requestedTargetKeys) {
		this.triggerTargets.removeIf(triggerTarget -> !requestedTargetKeys.contains(triggerTarget.getKey()));
	}
}
