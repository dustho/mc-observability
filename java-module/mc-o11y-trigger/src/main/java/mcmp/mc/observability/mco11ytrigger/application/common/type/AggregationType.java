package mcmp.mc.observability.mco11ytrigger.application.common.type;

import lombok.Getter;

@Getter
public enum AggregationType {
	MIN("min"), MAX("max"), AVG("avg"), LAST("last"),;

	private final String name;

	AggregationType(String value) {
		this.name = value;
	}
}
