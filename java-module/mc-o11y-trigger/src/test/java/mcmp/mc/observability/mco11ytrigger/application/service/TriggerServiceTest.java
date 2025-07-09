package mcmp.mc.observability.mco11ytrigger.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import mcmp.mc.observability.mco11ytrigger.adapter.alert.AlertManager;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.Trigger;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerPolicyRepository;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerRepository;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.GrafanaAlertRuleFactory;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.GrafanaClient;
import mcmp.mc.observability.mco11ytrigger.util.DummyFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@MockitoBean(types = {GrafanaAlertRuleFactory.class, GrafanaClient.class})
@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class TriggerServiceTest {

  @Autowired private TriggerService triggerService;

  @Autowired private TriggerPolicyRepository triggerPolicyRepository;

  @Autowired private TriggerRepository triggerRepository;

  @MockitoBean private AlertManager alertManager;

  @Test
  public void createTriggerPolicy() {
    TriggerPolicyCreateDto dto =
        TriggerPolicyCreateDto.builder()
            .title("title")
            .description("description")
            .thresholdCondition(new ThresholdCondition(30, 50, 70))
            .resourceType(ResourceType.CPU)
            .aggregationType(AggregationType.LAST)
            .holdDuration("0m")
            .repeatInterval("1m")
            .build();

    long triggerPolicyId = triggerService.createTriggerPolicy(dto);

    TriggerPolicy triggerPolicy = triggerPolicyRepository.findById(triggerPolicyId).orElse(null);
    assertNotNull(triggerPolicy);
  }

  @Test
  public void createTrigger() {
    TriggerPolicy triggerPolicy = triggerPolicyRepository.save(DummyFactory.triggerPolicy());
    TriggerCreateDto dto =
        TriggerCreateDto.builder()
            .title("title")
            .namespaceId("namespaceId")
            .targetId("targetId")
            .isActive(true)
            .build();
    doNothing().when(alertManager).createAlertRule(any());

    long triggerId = triggerService.createTrigger(triggerPolicy.getId(), dto);

    Trigger trigger = triggerRepository.findById(triggerId).orElse(null);
    assertNotNull(trigger);
  }
}
