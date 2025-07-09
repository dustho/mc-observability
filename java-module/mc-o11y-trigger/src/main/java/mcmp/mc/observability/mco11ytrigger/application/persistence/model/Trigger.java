package mcmp.mc.observability.mco11ytrigger.application.persistence.model;

import java.util.UUID;
import jakarta.persistence.*;

import lombok.Getter;

import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerCreateDto;

@Getter
@Table(name = "trigger")
@Entity
public class Trigger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uuid;

	private String title;

	private String namespaceId;

	private String targetId;

	private boolean isActive;

	@JoinColumn(name = "trigger_policy_snapshot_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private TriggerPolicySnapshot triggerPolicySnapshot;

	public static Trigger create(TriggerPolicySnapshot triggerPolicySnapshot, TriggerCreateDto dto) {
		Trigger entity = new Trigger();
		entity.uuid = UUID.randomUUID().toString();
		entity.title = dto.getTitle();
		entity.namespaceId = dto.getNamespaceId();
		entity.targetId = dto.getTargetId();
		entity.isActive = dto.isActive();
		entity.triggerPolicySnapshot = triggerPolicySnapshot;
		return entity;
	}
}
