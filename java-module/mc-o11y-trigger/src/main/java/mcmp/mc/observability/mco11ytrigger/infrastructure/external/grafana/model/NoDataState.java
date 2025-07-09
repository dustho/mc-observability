package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NoDataState {
	OK("OK"), ALERTING("Alerting"), NO_DATA("NoData");

	private final String value;
}
