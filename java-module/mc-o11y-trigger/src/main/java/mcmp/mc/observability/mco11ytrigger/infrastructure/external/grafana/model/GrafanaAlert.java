package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import java.util.List;
import java.util.Map;

public class GrafanaAlert {

	private Map<String, String> annotations;
	private Map<String, String> labels;
	private List<Map<String, String>> receivers;
	private Status status;
	private String fingerprint;
	private String startsAt;
	private String endsAt;
	private String updatedAt;
	private String generationUrl;

	static class Status {
		private List<String> inhibitedBy;
		private List<String> silencedBy;
		private String state;
	}
}
