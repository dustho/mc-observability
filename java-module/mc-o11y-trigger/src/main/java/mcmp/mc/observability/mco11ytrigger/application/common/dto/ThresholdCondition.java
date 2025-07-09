package mcmp.mc.observability.mco11ytrigger.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThresholdCondition {
	private double info;
	private double warning;
	private double critical;

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
