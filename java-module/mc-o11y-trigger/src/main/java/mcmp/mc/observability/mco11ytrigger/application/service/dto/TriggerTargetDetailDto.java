package mcmp.mc.observability.mco11ytrigger.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerTargetDetailDto {
	private long id;
	private String namespaceId;
	private String targetId;
	private boolean isActive;
}
