package mcmp.mc.observability.mco11ytrigger.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.TriggerTargetDto;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerPolicyCreateRequest;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerTargetUpdateRequest;
import mcmp.mc.observability.mco11ytrigger.application.service.TriggerService;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.CustomPageDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerTargetUpdateDto;
import mcmp.mc.observability.mco11ytrigger.util.ApiDocumentation;
import mcmp.mc.observability.mco11ytrigger.util.JsonConverter;

import org.junit.jupiter.api.Test;

import static mcmp.mc.observability.mco11ytrigger.util.ApiDocumentation.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@AutoConfigureMockMvc
@WebMvcTest(TriggerController.class)
class TriggerControllerTest {

	private static final String TAG = "TRIGGER-POLICY";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TriggerService triggerService;

	@Test
	void createTriggerPolicy() throws Exception {
		TriggerPolicyCreateRequest request = TriggerPolicyCreateRequest.builder().title("title")
				.description("description").thresholdCondition(new ThresholdCondition(30, 50, 70))
				.resourceType(ResourceType.CPU).aggregationType(AggregationType.LAST).holdDuration("0m")
				.repeatInterval("1m").build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/o11y/trigger/policy").contentType(MediaType.APPLICATION_JSON)
				.content(JsonConverter.asJsonString(request))).andExpect(status().isCreated())
				.andDo(ApiDocumentation.builder().tag(TAG).description("create trigger policy")
						.requestSchema("TriggerPolicyCreateRequest")
						.requestFields(fieldString("title", "title"), fieldString("description", "description"),
								fieldObject("thresholdCondition", "thresholdCondition"),
								fieldNumber("thresholdCondition.info", "threshold value of info"),
								fieldNumber("thresholdCondition.warning", "threshold value of warning"),
								fieldNumber("thresholdCondition.critical", "threshold value of critical"),
								fieldEnum("resourceType", "resource type [cpu | mem | disk]", ResourceType.class),
								fieldEnum("aggregationType", "aggregation type [max | min | mean | last]", AggregationType.class),
								fieldString("holdDuration", "minimum duration for firing alert"),
								fieldString("repeatInterval", "repeat interval of evaluation"))
						.build());

		verify(triggerService).createTriggerPolicy(any(TriggerPolicyCreateDto.class));
	}

	@Test
	void deleteTriggerPolicy() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/o11y/trigger/policy/{triggerPolicyId}", 1))
				.andExpect(status().isAccepted())
				.andDo(ApiDocumentation.builder().tag(TAG).description("delete trigger policy")
						.pathParameters(paramInteger("triggerPolicyId", "trigger policy id")).build());

		verify(triggerService).deleteTriggerPolicy(any(long.class));
	}

	@Test
	void updateTriggerTarget() throws Exception {
		TriggerTargetUpdateRequest request = new TriggerTargetUpdateRequest(List
				.of(TriggerTargetDto.builder().namespaceId("namespaceId").targetId("targetId").isActive(true).build()));

		mockMvc.perform(MockMvcRequestBuilders.put("/api/o11y/trigger/policy/{triggerPolicyId}/targets", 1)
				.contentType(MediaType.APPLICATION_JSON).content(JsonConverter.asJsonString(request)))
				.andExpect(status().isAccepted())
				.andDo(ApiDocumentation.builder().tag(TAG).description("update trigger policy")
						.requestSchema("TriggerTargetUpdateRequest")
						.pathParameters(paramInteger("triggerPolicyId", "trigger policy id"))
						.requestFields(fieldArray("triggerTargets", "List of trigger target objects"),
								fieldString("triggerTargets[].namespaceId", "namespace id"),
								fieldString("triggerTargets[].targetId", "target id"),
								fieldBoolean("triggerTargets[].isActive", "active status"))
						.build());

		verify(triggerService).updateTriggerTarget(any(long.class), any(TriggerTargetUpdateDto.class));
	}

	@Test
	void getTriggerPolicies() throws Exception {
		when(triggerService.getTriggerPolicies(any(Pageable.class))).thenReturn(CustomPageDto.empty());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/o11y/trigger/policy").param("page", "0").param("size", "10")
				.param("sortBy", "id").param("direction", "desc")).andExpect(status().isOk())
				.andDo(ApiDocumentation.builder().tag(TAG).description("Get page of trigger policies")
						.responseSchema("TriggerTargetPageResponse")
						.queryParameters(paramInteger("page", "page number (1 .. N)").optional(),
								paramInteger("size", "size of page (0 .. N)").optional(),
								paramInteger("sortBy", "sort by properties").optional(),
								paramInteger("direction", "sort of direction").optional())
						.responseFields(fieldArray("content", "trigger policy list"),
								fieldSubsection("pageable", "specific page info"),
								fieldNumber("totalPages", "total pages"),
								fieldNumber("totalElements", "total elements"),
								fieldNumber("numberOfElements", "number of elements"))
						.build());

		verify(triggerService).getTriggerPolicies(any(Pageable.class));
	}
}
