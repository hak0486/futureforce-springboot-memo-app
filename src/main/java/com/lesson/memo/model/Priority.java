package com.lesson.memo.model;

public enum Priority {
	HIGH(0),
	MEDIUM(1),
	LOW(2);
	
	private int priorityNum;
	
	private Priority(int num) {
		this.priorityNum = num;
	}
	
	public int getPriorityLevel() {
		return priorityNum;
	}
}
