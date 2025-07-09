package mcmp.mc.observability.mco11ytrigger.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import mcmp.mc.observability.mco11ytrigger.adapter.alert.AlertManager;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.TriggerTargetDto;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerTarget;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerPolicyRepository;
import mcmp.mc.observability.mco11ytrigger.application.persistence.repository.TriggerTargetRepository;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerTargetUpdateDto;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.GrafanaAlertRuleFactory;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.GrafanaClient;
import mcmp.mc.observability.mco11ytrigger.util.DummyFactory;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import static org.junit.jupiter.api.Assertions.*;

@MockitoBean(types = {GrafanaAlertRuleFactory.class, GrafanaClient.class})
@ActiveProfiles("test")
@SpringBootTest
public class TriggerServiceTest {

	@Autowired
	private TriggerService triggerService;

	@Autowired
	private TriggerPolicyRepository triggerPolicyRepository;

	@Autowired
	private TriggerTargetRepository triggerTargetRepository;

	@MockitoBean
	private AlertManager alertManager;

	@Transactional
	@Test
	public void createTriggerPolicy() {
		TriggerPolicyCreateDto dto = TriggerPolicyCreateDto.builder().title("title").description("description")
				.thresholdCondition(new ThresholdCondition(30, 50, 70)).resourceType(ResourceType.CPU)
				.aggregationType(AggregationType.LAST).holdDuration("0m").repeatInterval("1m").build();

		long triggerPolicyId = triggerService.createTriggerPolicy(dto);

		TriggerPolicy triggerPolicy = triggerPolicyRepository.findById(triggerPolicyId).orElse(null);
		assertNotNull(triggerPolicy);
	}

	@Transactional
	@Test
	public void addTriggerTarget() {
		TriggerPolicy triggerPolicy = triggerPolicyRepository.save(DummyFactory.triggerPolicy());
		TriggerTargetUpdateDto dto = new TriggerTargetUpdateDto(List
				.of(TriggerTargetDto.builder().namespaceId("namespaceId").targetId("targetId").isActive(true).build()));

		doNothing().when(alertManager).createAlertRule(any());
		doNothing().when(alertManager).deleteAlertRule(any());

		triggerService.updateTriggerTarget(triggerPolicy.getId(), dto);

		List<TriggerTarget> triggerTargets = triggerTargetRepository.findAll();
		assertNotNull(triggerTargets);
		assertEquals(1, triggerTargets.size());
	}

	@Transactional
	@Test
	public void updateTriggerTarget() {
		TriggerPolicy triggerPolicy = DummyFactory.triggerPolicy();
		TriggerTarget triggerTarget = DummyFactory.triggerTarget();
		triggerPolicy.addTriggerTarget(triggerTarget);
		triggerPolicyRepository.save(triggerPolicy);

		TriggerTargetUpdateDto dto = new TriggerTargetUpdateDto(List.of(
				TriggerTargetDto.builder().namespaceId("namespaceId1").targetId("targetId1").isActive(true).build(),
				TriggerTargetDto.builder().namespaceId("namespaceId2").targetId("targetId2").isActive(true).build()));

		doNothing().when(alertManager).createAlertRule(any());
		doNothing().when(alertManager).deleteAlertRule(any());

		triggerService.updateTriggerTarget(triggerPolicy.getId(), dto);

		List<TriggerTarget> triggerTargets = triggerTargetRepository.findAll();
		assertNotNull(triggerTargets);
		assertEquals(2, triggerTargets.size());
	}
}
