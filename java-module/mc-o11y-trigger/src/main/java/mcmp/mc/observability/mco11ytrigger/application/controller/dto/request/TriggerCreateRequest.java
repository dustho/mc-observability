package mcmp.mc.observability.mco11ytrigger.application.controller.dto.request;

import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
public record TriggerCreateRequest(String title, String namespaceId, String targetId,
		@JsonProperty("isActive") boolean isActive) {
}
