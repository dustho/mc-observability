package mcmp.mc.observability.mco11ytrigger.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mcmp.mc.observability.mco11ytrigger.application.common.dto.ThresholdCondition;
import mcmp.mc.observability.mco11ytrigger.application.common.type.AggregationType;
import mcmp.mc.observability.mco11ytrigger.application.common.type.ResourceType;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerTarget;

public class DummyFactory {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static TriggerPolicy triggerPolicy() {
		try {
			return TriggerPolicy.create("title", "description",
					objectMapper.writeValueAsString(new ThresholdCondition(10, 20, 30)), ResourceType.CPU.getField(),
					AggregationType.LAST.getName(), "1m", "1m");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static TriggerTarget triggerTarget() {
		return TriggerTarget.create("namespaceId", "targetId", true);
	}
}
