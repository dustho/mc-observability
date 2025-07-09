package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana;

import static mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model.GrafanaCondition.*;

import java.util.List;
import java.util.Map;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model.*;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model.GrafanaCondition.Evaluator;
import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model.GrafanaCondition.Operator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GrafanaAlertRuleFactory {

  @Value("${grafana.alert.orgId}")
  private Integer orgId;

  @Value("${grafana.alert.folderUid}")
  private String folderUid;

  @Value("${grafana.alert.ruleGroup.metric}")
  private String metricRuleGroup;

  @Value("${grafana.alert.receiver}")
  private String receiver;

  @Value("${grafana.alert.noDataState}")
  private String noDataState;

  @Value("${grafana.alert.execErrState}")
  private String execErrState;

  @Value("${grafana.alert.datasource.influx}")
  private String influxDatasourceUid;

  private final Map<String, String> metricLabels = Map.of("level", "{{ $values.C.Value }}");
  private final Map<String, String> metricAnnotations = Map.of("alertType", "metric");

  public GrafanaAlertRule createGrafanaAlertRule(
      String uid, String title, String duration, String query, String thresholdExpression) {
    List<GrafanaAlertQuery> alertData = createMetricAlertData(query, thresholdExpression);

    return GrafanaAlertRule.builder()
        .uid(uid)
        .annotations(metricAnnotations)
        .orgId(orgId)
        .folderUid(folderUid)
        .ruleGroup(metricRuleGroup)
        .title(title)
        .condition("C")
        .data(alertData)
        .noDataState(noDataState)
        .execErrState(execErrState)
        .duration(duration)
        .labels(metricLabels)
        .isPaused(true)
        .notificationSettings(
            GrafanaNotificationSetting.builder()
                .receiver(receiver)
                .groupWait("0s")
                .groupInterval("1s")
                .repeatInterval("1d")
                .build())
        .build();
  }

  private List<GrafanaAlertQuery> createMetricAlertData(String query, String thresholdExpression) {
    GrafanaAlertQuery dataA =
        GrafanaAlertQuery.builder()
            .refId("A")
            .queryType("")
            .relativeTimeRange(new TimeRange(600, 0))
            .datasourceUid(influxDatasourceUid)
            .model(
                GrafanaAlertModel.builder()
                    .datasource(
                        GrafanaDataSource.builder()
                            .type("influxdb")
                            .uid(influxDatasourceUid)
                            .build())
                    .query(query)
                    .intervalMs(1000)
                    .maxDataPoints(43200)
                    .rawQuery(true)
                    .refId("A")
                    .resultFormat("time_series")
                    .queryType("")
                    .build())
            .build();

    GrafanaAlertQuery dataB =
        GrafanaAlertQuery.builder()
            .refId("B")
            .queryType("")
            .relativeTimeRange(new TimeRange(10, 0))
            .datasourceUid("__expr__")
            .model(
                GrafanaAlertModel.builder()
                    .conditions(
                        List.of(
                            builder()
                                .type("query")
                                .evaluator(new Evaluator(List.of(0, 0), "gt"))
                                .operator(new Operator("and"))
                                .query(new Query(List.of()))
                                .reducer(new Reducer(List.of(), "avg"))
                                .build()))
                    .datasource(
                        GrafanaDataSource.builder()
                            .name("Expression")
                            .type("expr")
                            .uid("expr")
                            .build())
                    .expression("A")
                    .intervalMs(1000)
                    .maxDataPoints(43200)
                    .reducer("last")
                    .refId("B")
                    .settings(new GrafanaAlertModel.Settings("dropNN"))
                    .type("reduce")
                    .queryType("")
                    .build())
            .build();

    GrafanaAlertQuery dataC =
        GrafanaAlertQuery.builder()
            .refId("C")
            .queryType("")
            .relativeTimeRange(new TimeRange(0, 0))
            .datasourceUid("__expr__")
            .model(
                GrafanaAlertModel.builder()
                    .conditions(
                        List.of(
                            builder()
                                .type("query")
                                .evaluator(new Evaluator(List.of(0, 0), "gt"))
                                .operator(new Operator("and"))
                                .query(new Query(List.of()))
                                .reducer(new Reducer(List.of(), "avg"))
                                .build()))
                    .datasource(
                        GrafanaDataSource.builder()
                            .name("Expression")
                            .type("expr")
                            .uid("expr")
                            .build())
                    .expression(thresholdExpression)
                    .intervalMs(1000)
                    .maxDataPoints(43200)
                    .refId("C")
                    .type("math")
                    .queryType("")
                    .build())
            .build();

    return List.of(dataA, dataB, dataC);
  }
}
