package mcmp.mc.observability.mco11ytrigger.application.common.dto;

import lombok.Builder;

@Builder
public record TriggerTargetDto(String namespaceId, String targetId, boolean isActive) {
}
