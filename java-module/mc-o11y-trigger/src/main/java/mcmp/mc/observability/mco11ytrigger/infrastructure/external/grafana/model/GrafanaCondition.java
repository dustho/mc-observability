package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrafanaCondition {
  private String type;
  private Evaluator evaluator;
  private Operator operator;
  private Query query;
  private Reducer reducer;

  @Builder
  public GrafanaCondition(
      String type, Evaluator evaluator, Operator operator, Query query, Reducer reducer) {
    this.type = type;
    this.evaluator = evaluator;
    this.operator = operator;
    this.query = query;
    this.reducer = reducer;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Evaluator {
    private List<Integer> params;
    private String type;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Operator {
    private String type;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Query {
    private List<Integer> params;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Reducer {
    private List<Integer> params;
    private String type;
  }
}
