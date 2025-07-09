package mcmp.mc.observability.mco11ytrigger.application.persistence.repository;

import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggerPolicyRepository extends JpaRepository<TriggerPolicy, Long> {}
