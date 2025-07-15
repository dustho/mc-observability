package mcmp.mc.observability.mco11ytrigger.application.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mcmp.mc.observability.mco11ytrigger.application.persistence.model.TriggerPolicy;

@Repository
public interface TriggerPolicyRepository extends JpaRepository<TriggerPolicy, Long> {

	Page<TriggerPolicy> findAll(Pageable pageable);
}
