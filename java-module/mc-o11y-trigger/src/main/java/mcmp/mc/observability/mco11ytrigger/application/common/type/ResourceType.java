package mcmp.mc.observability.mco11ytrigger.application.common.type;

import lombok.Getter;

@Getter
public enum ResourceType {
  CPU("cpu", "usage_idle"),
  MEMORY("memory", "usage"),
  DISK("disk", "usage");

  private final String measurement;
  private final String field;

  ResourceType(String fieldName, String field) {
    this.measurement = fieldName;
    this.field = field;
  }
}
