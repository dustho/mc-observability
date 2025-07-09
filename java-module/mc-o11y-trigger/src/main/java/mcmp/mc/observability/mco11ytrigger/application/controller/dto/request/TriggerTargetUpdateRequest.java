package mcmp.mc.observability.mco11ytrigger.application.controller.dto.request;

import java.util.List;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.TriggerTargetDto;

public record TriggerTargetUpdateRequest(List<TriggerTargetDto> triggerTargets) {
}
