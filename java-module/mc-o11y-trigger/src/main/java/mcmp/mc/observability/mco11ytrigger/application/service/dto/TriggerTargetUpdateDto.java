package mcmp.mc.observability.mco11ytrigger.application.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import mcmp.mc.observability.mco11ytrigger.application.common.dto.TriggerTargetDto;
import mcmp.mc.observability.mco11ytrigger.application.controller.dto.request.TriggerTargetUpdateRequest;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TriggerTargetUpdateDto {

	private List<TriggerTargetDto> triggerTargetDtos;

	public static TriggerTargetUpdateDto from(TriggerTargetUpdateRequest req) {
		TriggerTargetUpdateDto dto = new TriggerTargetUpdateDto();
		dto.triggerTargetDtos = req.triggerTargets();
		return dto;
	}
}
