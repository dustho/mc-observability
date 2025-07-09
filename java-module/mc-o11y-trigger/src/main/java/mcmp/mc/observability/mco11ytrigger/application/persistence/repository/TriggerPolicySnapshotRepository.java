package mcmp.mc.observability.mco11ytrigger.application.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicySnapshot;

@Repository
public interface TriggerPolicySnapshotRepository extends JpaRepository<TriggerPolicySnapshot, Long> {

	TriggerPolicySnapshot findByTriggerPolicyIdAndVersion(long triggerPolicyId, int version);
}
