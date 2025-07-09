package mcmp.mc.observability.mco11ytrigger.application.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TriggerCreateRequest(
    String title,
    String namespaceId,
    String targetId,
    @JsonProperty("isActive") boolean isActive) {}
