package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {
  private Integer from;
  private Integer to;
}
