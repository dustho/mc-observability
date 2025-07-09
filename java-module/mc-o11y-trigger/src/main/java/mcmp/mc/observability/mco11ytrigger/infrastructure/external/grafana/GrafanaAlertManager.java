package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import mcmp.mc.observability.mco11ytrigger.adapter.alert.AlertManager;
import mcmp.mc.observability.mco11ytrigger.adapter.alert.dto.AlertRuleCreateDto;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model.GrafanaAlertRule;

@Component
public class GrafanaAlertManager implements AlertManager {

	private final GrafanaClient grafanaClient;
	private final GrafanaAlertRuleFactory alertRuleFactory;

	public GrafanaAlertManager(GrafanaClient grafanaClient, GrafanaAlertRuleFactory alertRuleFactory) {
		this.grafanaClient = grafanaClient;
		this.alertRuleFactory = alertRuleFactory;
	}

	@Override
	public void createAlertRule(AlertRuleCreateDto dto) {
		String query = Flux.from("cmp/autogen").range(-2L, -1L, ChronoUnit.MINUTES)
				.filter(Restrictions.and(Restrictions.measurement().equal(dto.getMeasurement()),
						Restrictions.field().equal(dto.getField()),
						Restrictions.tag("target_id").equal(dto.getTargetId()),
						Restrictions.tag("ns_id").equal(dto.getNamespaceId())))
				.map("({ r with _value: 100.0 - r._value })")
				.aggregateWindow(1L, ChronoUnit.MINUTES, dto.getAggregation())
				.groupBy(new String[]{"target_id", "ns_id"}).toString();

		GrafanaAlertRule alertRule = alertRuleFactory.createGrafanaAlertRule(dto.getUuid(), dto.getTitle(),
				dto.getHoldDuration(), query, dto.getThresholdExpression());
		grafanaClient.createAlertRule(alertRule);
	}

	@Override
	public void deleteAlertRule(String uuid) {
		grafanaClient.deleteAlertRule(uuid);
	}
}
