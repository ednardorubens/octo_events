package jaya.tech.octo_events;

import org.junit.jupiter.api.Test;

class OctoEventsApplicationTests {

	@Test
	void test() {
		OctoEventsApplication.main(new String[] {
			"--spring.profiles.active=test",
			"--spring.main.web-environment=false",
			"--spring.cloud.discovery.enabled=false",
			"--logging.level.root=OFF",
			"--logging.level.org.hibernate.type=OFF"
        });
	}

}
