package com.daan.pws.match.enums;

public enum NotificationPriority {

	NORMAL, HIGH, HIGHEST;

	public boolean isHigherThan(NotificationPriority priority) {
		switch (priority) {
		case NORMAL:
			return this == HIGH || this == HIGHEST;
		case HIGH:
			return this == HIGHEST;
		default:
			return false;
		}
	}

}
