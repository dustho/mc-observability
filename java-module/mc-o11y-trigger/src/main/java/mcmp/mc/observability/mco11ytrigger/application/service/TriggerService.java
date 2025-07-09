package mcmp.mc.observability.mco11ytrigger.application.service;

import mcmp.mc.observability.mco11ytrigger.adapter.alert.AlertManager;
import mcmp.mc.observability.mco11ytrigger.adapter.alert.dto.AlertRuleCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.Trigger;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicySnapshot;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerPolicyRepository;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerPolicySnapshotRepository;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerRepository;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TriggerService {

  private final TriggerPolicyRepository triggerPolicyRepository;
  private final TriggerPolicySnapshotRepository triggerPolicySnapshotRepository;
  private final TriggerRepository triggerRepository;
  private final AlertManager alertManager;

  public TriggerService(
      TriggerPolicyRepository triggerPolicyRepository,
      TriggerPolicySnapshotRepository triggerPolicySnapshotRepository,
      TriggerRepository triggerRepository,
      AlertManager alertManager) {
    this.triggerPolicyRepository = triggerPolicyRepository;
    this.triggerPolicySnapshotRepository = triggerPolicySnapshotRepository;
    this.triggerRepository = triggerRepository;
    this.alertManager = alertManager;
  }

  public long createTriggerPolicy(TriggerPolicyCreateDto triggerPolicyCreateDto) {
    TriggerPolicy triggerPolicy = TriggerPolicy.create(triggerPolicyCreateDto);
    triggerPolicy = triggerPolicyRepository.save(triggerPolicy);
    return triggerPolicy.getId();
  }

  public long createTrigger(long id, TriggerCreateDto triggerCreateDto) {
    TriggerPolicy triggerPolicy =
        triggerPolicyRepository.findById(id).orElseThrow(RuntimeException::new);
    TriggerPolicySnapshot triggerPolicySnapshot =
        triggerPolicySnapshotRepository.findByTriggerPolicyIdAndVersion(
            triggerPolicy.getId(), triggerPolicy.getVersion());
    if (triggerPolicySnapshot == null) {
      triggerPolicySnapshot =
          triggerPolicySnapshotRepository.save(TriggerPolicySnapshot.create(triggerPolicy));
    }
    Trigger trigger = Trigger.create(triggerPolicySnapshot, triggerCreateDto);
    trigger = triggerRepository.save(trigger);
    alertManager.createAlertRule(AlertRuleCreateDto.from(trigger, triggerPolicy));
    return trigger.getId();
  }
}
