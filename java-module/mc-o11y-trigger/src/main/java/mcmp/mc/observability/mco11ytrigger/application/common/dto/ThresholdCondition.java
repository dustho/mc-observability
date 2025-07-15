package mcmp.mc.observability.mco11ytrigger.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThresholdCondition {
	private double info;
	private double warning;
	private double critical;

	public static ThresholdCondition from(String json) {
		try {
			return new ObjectMapper().readValue(json, ThresholdCondition.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public String toJson() {
		return """
				{
				    "info": "%s",
				    "warning": "%s",
				    "critical": "%s"
				}
				""".formatted(info, warning, critical);
	}
}
