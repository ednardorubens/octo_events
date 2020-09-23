package jaya.tech.octo_events.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jaya.tech.octo_events.model.Event;
import jaya.tech.octo_events.service.EventService;

@RestController
public class EventController {

	public static final String EVENTS = "/events";

	@Autowired
	private EventService eventService;

	@PostMapping(EventController.EVENTS)
	@CacheEvict(cacheNames = { "events_id", "events_number" })
	public ResponseEntity<Event> save(@Valid @RequestBody final Event event, final UriComponentsBuilder uriBuilder) {
		final Event saved = eventService.save(event);
		final URI uri = uriBuilder.path(EventController.EVENTS + "/{id}").buildAndExpand(saved.getId()).toUri();
		return ResponseEntity.created(uri).body(saved);
	}

	@GetMapping(EventController.EVENTS + "/{id}")
	@Cacheable(cacheNames = "events_id", key = "#id")
	public ResponseEntity<Event> find(@PathVariable Long id, final UriComponentsBuilder uriBuilder) {
		return eventService.findById(id).map(evt -> ResponseEntity.ok(evt)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/issues/{number}/events")
	@Cacheable(cacheNames = "events_number", key = "#number")
	public Page<Event> getEventByNumber(@PathVariable Long number, Pageable page) {
		return eventService.findByIssueNumber(number, page);
	}
}
