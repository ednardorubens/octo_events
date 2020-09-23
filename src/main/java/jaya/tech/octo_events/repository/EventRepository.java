package jaya.tech.octo_events.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import jaya.tech.octo_events.model.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

	Page<Event> findByIssueNumber(Long number, Pageable page);

}