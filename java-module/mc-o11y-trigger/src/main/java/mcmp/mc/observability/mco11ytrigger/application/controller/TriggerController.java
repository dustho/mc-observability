package mcmp.mc.observability.mco11ytrigger.application.controller;

import java.net.URI;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerCreateRequest;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerPolicyCreateRequest;
import mcmp.mc.observability.mco11ytrigger.application.service.TriggerService;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/o11y/trigger/policy")
public class TriggerController {

  private final TriggerService triggerService;

  public TriggerController(TriggerService triggerService) {
    this.triggerService = triggerService;
  }

  @PostMapping
  public ResponseEntity<?> createTriggerPolicy(@RequestBody TriggerPolicyCreateRequest request) {
    long triggerPolicyId = triggerService.createTriggerPolicy(TriggerPolicyCreateDto.from(request));
    return ResponseEntity.created(URI.create("/api/o11y/trigger/policy/" + triggerPolicyId))
        .build();
  }

  @PostMapping("/{id}")
  public ResponseEntity<?> createTrigger(
      @PathVariable long id, @RequestBody TriggerCreateRequest request) {
    long triggerId = triggerService.createTrigger(id, TriggerCreateDto.from(request));
    return ResponseEntity.created(URI.create("/api/o11y/trigger/" + triggerId)).build();
  }
}
