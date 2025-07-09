package mcmp.mc.observability.mco11ytrigger.application.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerTarget;

@Repository
public interface TriggerTargetRepository extends JpaRepository<TriggerTarget, Long> {

	List<TriggerTarget> findByTriggerPolicy(TriggerPolicy triggerPolicy);
}
