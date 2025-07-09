package mcmp.mc.observability.mco11ytrigger.application.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mcmp.mc.observability.mco11ytrigger.application.persistence.model.Trigger;

@Repository
public interface TriggerRepository extends JpaRepository<Trigger, Long> {
}
