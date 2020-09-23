package jaya.tech.octo_events.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jaya.tech.octo_events.model.Event;
import jaya.tech.octo_events.model.Issue;
import jaya.tech.octo_events.repository.EventRepository;
import jaya.tech.octo_events.repository.IssueRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private IssueRepository issueRepository;

	@Transactional
	public Event save(final Event event) {
		EventService.log.debug("saving: " + event);
		final Issue issueReceived = event.getIssue();
		issueRepository.findById(issueReceived.getId()).ifPresent(issueInDB -> {
			BeanUtils.copyProperties(issueReceived, issueInDB);
			event.setIssue(issueRepository.save(issueInDB));
		});
		return eventRepository.save(event);
	}

	public Optional<Event> findById(final Long id) {
		EventService.log.debug("searching event " + id);
		return eventRepository.findById(id);
	}

	public Page<Event> findByIssueNumber(final Long number,final Pageable page) {
		EventService.log.debug("searching events by number " + number);
		return eventRepository.findByIssueNumber(number, page);
	}

}
