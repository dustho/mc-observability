package mcmp.mc.observability.mco11ytrigger.application.controller.dto.request;

import lombok.Builder;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;

@Builder
public record TriggerPolicyCreateRequest(
    String title,
    String description,
    ThresholdCondition thresholdCondition,
    ResourceType resourceType,
    AggregationType aggregationType,
    String holdDuration,
    String repeatInterval) {}
