package mcmp.mc.observability.mco11ytrigger.application.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mcmp.mc.observability.mco11ytrigger.adapter.alert.AlertManager;
import mcmp.mc.observability.mco11ytrigger.adapter.alert.dto.AlertRuleCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.TriggerTargetDto;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerTarget;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerPolicyRepository;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerTargetUpdateDto;

@Transactional
@Service
public class TriggerService {

	private final TriggerPolicyRepository triggerPolicyRepository;
	private final AlertManager alertManager;

	public TriggerService(TriggerPolicyRepository triggerPolicyRepository, AlertManager alertManager) {
		this.triggerPolicyRepository = triggerPolicyRepository;
		this.alertManager = alertManager;
	}

	public long createTriggerPolicy(TriggerPolicyCreateDto triggerPolicyCreateDto) {
		TriggerPolicy triggerPolicy = TriggerPolicy.create(triggerPolicyCreateDto);
		triggerPolicy = triggerPolicyRepository.save(triggerPolicy);
		return triggerPolicy.getId();
	}

	public void updateTriggerTarget(long id, TriggerTargetUpdateDto triggerTargetUpdateDto) {
		TriggerPolicy triggerPolicy = triggerPolicyRepository.findById(id).orElseThrow(RuntimeException::new);
		if (triggerPolicy.getTriggerTargets().isEmpty()) {
			triggerTargetUpdateDto.getTriggerTargetDtos().stream().map(TriggerTarget::create)
					.forEach(triggerPolicy::addTriggerTarget);
		} else {
			addTriggerTarget(triggerPolicy, triggerTargetUpdateDto);
			removeTriggerTarget(triggerPolicy, triggerTargetUpdateDto);
		}
		triggerPolicyRepository.save(triggerPolicy);
	}

	private void addTriggerTarget(TriggerPolicy triggerPolicy, TriggerTargetUpdateDto triggerTargetUpdateDto) {
		List<TriggerTarget> savedTriggerTargets = triggerPolicy.getTriggerTargets();
		Set<String> savedTargetKeys = savedTriggerTargets.stream().map(target -> {
			target.setupKey();
			return target.getKey();
		}).collect(Collectors.toSet());

		List<TriggerTargetDto> triggerTargetDtos = triggerTargetUpdateDto.getTriggerTargetDtos();
		for (TriggerTargetDto triggerTargetDto : triggerTargetDtos) {
			String key = triggerTargetDto.namespaceId() + triggerTargetDto.targetId();
			if (!savedTargetKeys.contains(key)) {
				TriggerTarget triggerTarget = TriggerTarget.create(triggerTargetDto);
				triggerPolicy.addTriggerTarget(triggerTarget);
				alertManager.createAlertRule(AlertRuleCreateDto.from(triggerPolicy, triggerTarget));
			}
		}
	}

	private void removeTriggerTarget(TriggerPolicy triggerPolicy, TriggerTargetUpdateDto triggerTargetUpdateDto) {
		List<TriggerTargetDto> triggerTargetDtos = triggerTargetUpdateDto.getTriggerTargetDtos();
		Set<String> requestedTargetKeys = triggerTargetDtos.stream()
				.map(targetDto -> targetDto.namespaceId() + targetDto.targetId()).collect(Collectors.toSet());
		triggerPolicy.removeIfNotContains(requestedTargetKeys);
	}

	public void deleteTriggerPolicy(long id) {
		TriggerPolicy triggerPolicy = triggerPolicyRepository.findById(id).orElseThrow(RuntimeException::new);
		List<TriggerTarget> triggerTargets = triggerPolicy.getTriggerTargets();
		for (TriggerTarget triggerTarget : triggerTargets) {
			alertManager.deleteAlertRule(triggerTarget.getUuid());
		}
		triggerPolicyRepository.deleteById(id);
	}
}
