package jaya.tech.octo_events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OctoEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OctoEventsApplication.class, args);
	}

}
