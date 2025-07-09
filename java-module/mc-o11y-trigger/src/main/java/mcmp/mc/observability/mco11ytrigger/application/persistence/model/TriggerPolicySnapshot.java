package mcmp.mc.observability.mco11ytrigger.application.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "trigger_policy_snapshot")
@Entity
public class TriggerPolicySnapshot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private long triggerPolicyId;

  private int version;

  private String title;

  private String description;

  private String thresholdCondition;

  private String resourceType;

  private String aggregationType;

  private String holdDuration;

  private String repeatInterval;

  public static TriggerPolicySnapshot create(TriggerPolicy triggerPolicy) {
    TriggerPolicySnapshot entity = new TriggerPolicySnapshot();
    entity.triggerPolicyId = triggerPolicy.getId();
    entity.version = triggerPolicy.getVersion();
    entity.title = triggerPolicy.getTitle();
    entity.description = triggerPolicy.getDescription();
    entity.thresholdCondition = triggerPolicy.getThresholdCondition();
    entity.resourceType = triggerPolicy.getResourceType();
    entity.aggregationType = triggerPolicy.getAggregationType();
    entity.holdDuration = triggerPolicy.getHoldDuration();
    entity.repeatInterval = triggerPolicy.getRepeatInterval();
    return entity;
  }
}
