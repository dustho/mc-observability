package mcmp.mc.observability.mco11ytrigger.adapter.alert;

import mcmp.mc.observability.mco11ytrigger.adapter.alert.dto.AlertRuleCreateDto;

public interface AlertManager {
	void createAlertRule(AlertRuleCreateDto dto);
	void deleteAlertRule(String uuid);
}
