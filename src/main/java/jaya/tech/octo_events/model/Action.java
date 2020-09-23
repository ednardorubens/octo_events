package jaya.tech.octo_events.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Action {

	OPENED("opened"),
	EDITED("edited"),
	DELETED("deleted"),
	PINNED("pinned"),
	UNPINNED("unpinned"),
	CLOSED("closed"),
	REOPENED("reopened"),
	ASSIGNED("assigned"),
	UNASSIGNED("unassigned"),
	LABELED("labeled"),
	UNLABELED("unlabeled"),
	LOCKED("locked"),
	UNLOCKED("unlocked"),
	TRANSFERRED("transferred"),
	MILESTONED("milestoned"),
	DEMILESTONED("demilestoned");

	private final String description;

	private Action(String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

}
