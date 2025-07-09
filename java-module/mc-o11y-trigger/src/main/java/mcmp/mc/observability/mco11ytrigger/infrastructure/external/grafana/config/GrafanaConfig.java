package mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.config;

import mcmp.mc.observability.mco11ytrigger.infrastructure.external.grafana.GrafanaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class GrafanaConfig {

  @Bean
  public GrafanaClient grafanaClient() {
    return HttpServiceProxyFactory.builder()
        .exchangeAdapter(WebClientAdapter.create(WebClient.builder().build()))
        .build()
        .createClient(GrafanaClient.class);
  }
}
