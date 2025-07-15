package mcmp.mc.observability.mco11ytrigger.application.persistence.model;

import java.util.UUID;
import jakarta.persistence.*;

import lombok.Getter;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.TriggerTargetDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerTargetDetailDto;

@Getter
@Table(name = "trigger_target")
@Entity
public class TriggerTarget {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String uuid;

	private String namespaceId;

	private String targetId;

	private boolean isActive;

	@JoinColumn(name = "trigger_policy_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private TriggerPolicy triggerPolicy;

	@Transient
	private String key;

	public static TriggerTarget create(TriggerTargetDto dto) {
		TriggerTarget entity = new TriggerTarget();
		entity.uuid = UUID.randomUUID().toString();
		entity.namespaceId = dto.namespaceId();
		entity.targetId = dto.targetId();
		entity.isActive = dto.isActive();
		entity.setupKey();
		return entity;
	}

	public static TriggerTarget create(String namespaceId, String targetId, boolean isActive) {
		TriggerTarget entity = new TriggerTarget();
		entity.uuid = UUID.randomUUID().toString();
		entity.namespaceId = namespaceId;
		entity.targetId = targetId;
		entity.isActive = isActive;
		entity.setupKey();
		return entity;
	}

	public TriggerTargetDetailDto toDto() {
		return TriggerTargetDetailDto.builder().id(id).targetId(targetId).namespaceId(namespaceId).isActive(isActive)
				.build();
	}

	public void setupKey() {
		key = namespaceId + targetId;
	}

	public void setTriggerPolicy(TriggerPolicy triggerPolicy) {
		this.triggerPolicy = triggerPolicy;
	}
}
