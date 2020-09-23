package jaya.tech.octo_events.repository;

import org.springframework.data.repository.CrudRepository;

import jaya.tech.octo_events.model.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {}