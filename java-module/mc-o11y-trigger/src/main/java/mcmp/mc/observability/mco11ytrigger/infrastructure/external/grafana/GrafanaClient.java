package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana;

import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.model.GrafanaAlertRule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface GrafanaClient {

  @PostExchange("/api/v1/provisioning/alert-rules")
  ResponseEntity<Object> createAlertRule(@RequestBody GrafanaAlertRule dto);

  @DeleteExchange("/api/v1/provisioning/alert-rules/{uid}")
  ResponseEntity<Void> deleteAlertRule(@PathVariable String uid);
}
