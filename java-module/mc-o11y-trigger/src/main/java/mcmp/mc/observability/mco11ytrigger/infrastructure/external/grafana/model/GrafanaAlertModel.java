package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import java.util.List;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrafanaAlertModel {
	private GrafanaDataSource datasource;
	private Integer intervalMs;
	private Integer maxDataPoints;
	private String query;
	private boolean rawQuery;
	private List<GrafanaCondition> conditions;
	private String expression;
	private String reducer;
	private String refId;
	private String resultFormat;
	private Settings settings;
	private String type;
	private String queryType;
	private String expr;
	private String editorMode;

	@Builder
	public GrafanaAlertModel(GrafanaDataSource datasource, Integer intervalMs, Integer maxDataPoints, String query,
			boolean rawQuery, List<GrafanaCondition> conditions, String expression, String reducer, String refId,
			String resultFormat, Settings settings, String type, String queryType, String expr, String editorMode) {
		this.datasource = datasource;
		this.intervalMs = intervalMs;
		this.maxDataPoints = maxDataPoints;
		this.query = query;
		this.rawQuery = rawQuery;
		this.conditions = conditions;
		this.expression = expression;
		this.reducer = reducer;
		this.refId = refId;
		this.resultFormat = resultFormat;
		this.settings = settings;
		this.type = type;
		this.queryType = queryType;
		this.expr = expr;
		this.editorMode = editorMode;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Settings {
		private String mode;
	}
}
