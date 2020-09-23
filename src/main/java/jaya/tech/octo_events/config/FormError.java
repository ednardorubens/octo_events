package jaya.tech.octo_events.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormError {

	private final String field;
	private final String error;
}
