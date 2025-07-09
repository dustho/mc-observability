package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExecErrState {
  OK("OK"),
  ALERTING("Alerting"),
  ERROR("Error");

  private final String value;
}
