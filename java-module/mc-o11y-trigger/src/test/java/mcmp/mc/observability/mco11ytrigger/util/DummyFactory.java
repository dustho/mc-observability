package mcmp.mc.observability.mco11ytrigger.util;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;

public class DummyFactory {

  public static TriggerPolicy triggerPolicy() {
    return TriggerPolicy.create(
        "title",
        "description",
        new ThresholdCondition(10, 20, 30).toString(),
        ResourceType.CPU.getField(),
        AggregationType.LAST.getName(),
        "1m",
        "1m");
  }
}
