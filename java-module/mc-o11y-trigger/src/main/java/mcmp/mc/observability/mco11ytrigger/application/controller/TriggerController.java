package mcmp.mc.observability.mco11ytrigger.application.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerPolicyCreateRequest;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerTargetUpdateRequest;
import mcmp.mc.observability.mco11ytrigger.application.service.TriggerService;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerTargetUpdateDto;

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
		return ResponseEntity.created(URI.create("/api/o11y/trigger/policy/" + triggerPolicyId)).build();
	}

	@PostMapping("/{id}/targets")
	public ResponseEntity<?> updateTriggerTarget(@PathVariable long id,
			@RequestBody TriggerTargetUpdateRequest request) {
		triggerService.updateTriggerTarget(id, TriggerTargetUpdateDto.from(request));
		return ResponseEntity.accepted().build();
	}
}
