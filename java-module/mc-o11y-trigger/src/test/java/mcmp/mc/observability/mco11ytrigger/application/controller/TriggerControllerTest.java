package mcmp.mc.observability.mco11ytrigger.application.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerCreateRequest;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerPolicyCreateRequest;
import mcmp.mc.observability.mco11ytrigger.application.service.TriggerService;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.util.JsonConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@AutoConfigureMockMvc
@WebMvcTest(TriggerController.class)
class TriggerControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private TriggerService triggerService;

  @Test
  void createTriggerPolicy() throws Exception {
    TriggerPolicyCreateRequest request =
        TriggerPolicyCreateRequest.builder()
            .title("title")
            .description("description")
            .thresholdCondition(new ThresholdCondition(30, 50, 70))
            .resourceType(ResourceType.CPU)
            .aggregationType(AggregationType.LAST)
            .holdDuration("0m")
            .repeatInterval("1m")
            .build();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/o11y/trigger/policy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.asJsonString(request)))
        .andExpect(status().isCreated())
        .andDo(
            document(
                "trigger-policy-create",
                requestFields(
                    fieldWithPath("title").description("title").type(String.class),
                    fieldWithPath("description").description("description").type(String.class),
                    fieldWithPath("thresholdCondition.info")
                        .description("threshold value of info")
                        .type(int.class),
                    fieldWithPath("thresholdCondition.warning")
                        .description("threshold value of warning")
                        .type(int.class),
                    fieldWithPath("thresholdCondition.critical")
                        .description("threshold value of critical")
                        .type(int.class),
                    fieldWithPath("resourceType")
                        .description("resource type [cpu | mem | disk]")
                        .type(String.class),
                    fieldWithPath("aggregationType")
                        .description("aggregation type [max | min | mean | last]")
                        .type(String.class),
                    fieldWithPath("holdDuration")
                        .description("minimum duration for firing alert")
                        .type(String.class),
                    fieldWithPath("repeatInterval")
                        .description("repeat interval of evaluation")
                        .type(String.class))));

    verify(triggerService).createTriggerPolicy(any(TriggerPolicyCreateDto.class));
  }

  @Test
  void createTrigger() throws Exception {
    TriggerCreateRequest request =
        TriggerCreateRequest.builder()
            .title("title")
            .namespaceId("namespaceId")
            .targetId("targetId")
            .isActive(true)
            .build();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/o11y/trigger/policy/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.asJsonString(request)))
        .andExpect(status().isCreated())
        .andDo(
            document(
                "trigger-create",
                requestFields(
                    fieldWithPath("title").description("title").type(String.class),
                    fieldWithPath("namespaceId").description("namespace id").type(String.class),
                    fieldWithPath("targetId").description("target id").type(String.class),
                    fieldWithPath("isActive").description("active status").type(boolean.class))));

    verify(triggerService).createTrigger(any(long.class), any(TriggerCreateDto.class));
  }
}
