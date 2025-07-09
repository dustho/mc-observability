package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrafanaAlertQuery {
	private String refId;
	private String queryType;
	private TimeRange relativeTimeRange;
	private String datasourceUid;
	private GrafanaAlertModel model;

	@Builder
	public GrafanaAlertQuery(String refId, String queryType, TimeRange relativeTimeRange, String datasourceUid,
			GrafanaAlertModel model) {
		this.refId = refId;
		this.queryType = queryType;
		this.relativeTimeRange = relativeTimeRange;
		this.datasourceUid = datasourceUid;
		this.model = model;
	}
}
