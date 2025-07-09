package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrafanaDataSource {
  private String name;
  private String type;
  private String uid;

  @Builder
  public GrafanaDataSource(String name, String type, String uid) {
    this.name = name;
    this.type = type;
    this.uid = uid;
  }
}
