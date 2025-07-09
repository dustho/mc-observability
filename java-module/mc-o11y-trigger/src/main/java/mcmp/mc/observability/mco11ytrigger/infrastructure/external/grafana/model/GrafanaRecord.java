package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrafanaRecord {
	@NotNull private String from;
	@NotNull private String metric;
}
