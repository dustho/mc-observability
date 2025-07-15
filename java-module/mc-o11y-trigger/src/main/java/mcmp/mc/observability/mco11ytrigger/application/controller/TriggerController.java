package mcmp.mc.observability.mco11ytrigger.application.controller;

import java.net.URI;

import mcmp.mc.observability.mco11ytrigger.application.controller.dto.response.TriggerPolicyPageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerPolicyCreateRequest;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerTargetUpdateRequest;
import mcmp.mc.observability.mco11ytrigger.application.service.TriggerService;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.CustomPageDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyCreateDto;
import mcmp.mc.observability.mco11ytrigger.application.service.dto.TriggerPolicyDetailDto;
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

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTriggerPolicy(@PathVariable long id) {
		triggerService.deleteTriggerPolicy(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping
	public ResponseEntity<?> getTriggerPolicies(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "desc") String direction) {
		Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		CustomPageDto<TriggerPolicyDetailDto> triggerPolicies = triggerService.getTriggerPolicies(pageable);
		return ResponseEntity.ok(TriggerPolicyPageResponse.from(triggerPolicies));
	}

	@PutMapping("/{id}/targets")
	public ResponseEntity<?> updateTriggerTarget(@PathVariable long id,
			@RequestBody TriggerTargetUpdateRequest request) {
		triggerService.updateTriggerTarget(id, TriggerTargetUpdateDto.from(request));
		return ResponseEntity.accepted().build();
	}
}
